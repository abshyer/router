package cn.shyman.library.router.compiler;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.WildcardTypeName;

import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedOptions;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

import cn.shyman.library.router.annotations.Route;

import static javax.lang.model.element.Modifier.PUBLIC;

@AutoService(Processor.class)
@SupportedOptions(Const.MODULE_NAME)
@SupportedSourceVersion(SourceVersion.RELEASE_7)
@SupportedAnnotationTypes(Const.ANNOTATION_TYPE_ROUTE)
public class RouteProcessor extends AbstractProcessor {
	private Logger logger;
	
	private Filer filer;
	private Elements elements;
	private Types types;
	
	private String moduleName;
	
	private Map<String, String> routeModuleMap = new HashMap<>();
	private Map<String, Set<RouteInfo>> routeGroupMap = new HashMap<>();
	
	@Override
	public synchronized void init(ProcessingEnvironment processingEnvironment) {
		super.init(processingEnvironment);
		
		this.logger = new Logger(processingEnvironment.getMessager());
		
		this.filer = processingEnvironment.getFiler();
		this.elements = processingEnvironment.getElementUtils();
		this.types = processingEnvironment.getTypeUtils();
		
		Map<String, String> options = processingEnvironment.getOptions();
		this.moduleName = options.get(Const.MODULE_NAME);
		if (this.moduleName == null) {
			throw new RuntimeException("Must config moduleName at defaultConfig. Example: " +
					"\njavaCompileOptions {\n" +
					"\t\t\tannotationProcessorOptions {\n" +
					"\t\t\t\targuments = [ moduleName : project.getName() ]\n" +
					"\t\t\t}\n" +
					"\t\t}");
		}
	}
	
	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnvironment) {
		if (annotations == null || annotations.isEmpty()) {
			return false;
		}
		
		Set<? extends Element> routeElements = roundEnvironment.getElementsAnnotatedWith(Route.class);
		if (routeElements.isEmpty()) {
			return true;
		}
		
		try {
			createJavaFile(routeElements);
		} catch (IOException e) {
			this.logger.error("Create Java File failure : " + e.getMessage());
		}
		
		return true;
	}
	
	private void createJavaFile(Set<? extends Element> routeElements) throws IOException {
		this.routeModuleMap.clear();
		
		TypeMirror androidActivityTypeMirror = elements.getTypeElement(Const.TYPE_ANDROID_ACTIVITY).asType();
		TypeMirror routeProviderTypeMirror = this.elements.getTypeElement(Const.TYPE_I_ROUTE_PROVIDER).asType();
		
		TypeElement iRouteGroup = this.elements.getTypeElement(Const.TYPE_I_ROUTE_GROUP);
		
		ClassName routeMetaClassName = ClassName.get(Const.PACKAGE_TEMPLATE, Const.ROUTE_META);
		ClassName routeTypeClassName = ClassName.get(Const.PACKAGE_TEMPLATE, Const.ROUTE_TYPE);
		
		/*
		 Map<String, Class<? extends IRouteGroup>> routeGroupMap
		*/
		ParameterSpec routeGroupMapParameter = ParameterSpec.builder(
				ParameterizedTypeName.get(
						ClassName.get(Map.class),
						ClassName.get(String.class),
						ParameterizedTypeName.get(
								ClassName.get(Class.class),
								WildcardTypeName.subtypeOf(ClassName.get(iRouteGroup))
						)),
				Const.METHOD_LOAD_ROUTE_GROUP_PARAMETER)
				.build();

		/*
		 public void loadRoute(Map<String, Class<? extends IRouteGroup>> routeGroupMap)
		*/
		MethodSpec.Builder loadRouteGroupMethod = MethodSpec.methodBuilder(Const.METHOD_LOAD_ROUTE)
				.addAnnotation(Override.class)
				.addModifiers(Modifier.PUBLIC)
				.addParameter(routeGroupMapParameter);

		/*
		 Map<String, RouteMeta> routeMetaMap
		*/
		ParameterSpec routeMetaMapParameter = ParameterSpec.builder(
				ParameterizedTypeName.get(ClassName.get(Map.class), ClassName.get(String.class), routeMetaClassName),
				Const.METHOD_LOAD_ROUTE_META_PARAMETER)
				.build();
		
		for (Element routeElement : routeElements) {
			TypeMirror routeElementTypeMirror = routeElement.asType();
			Route route = routeElement.getAnnotation(Route.class);
			
			RouteInfo routeInfo = null;
			if (this.types.isSubtype(routeElementTypeMirror, androidActivityTypeMirror)) {
				routeInfo = new RouteInfo(Const.ROUTE_TYPE_ACTIVITY, routeElement, route.value());
			} else if (this.types.isSubtype(routeElementTypeMirror, routeProviderTypeMirror)) {
				routeInfo = new RouteInfo(Const.ROUTE_TYPE_PROVIDER, routeElement, route.value());
			}
			categories(routeInfo);
		}
		
		for (Map.Entry<String, Set<RouteInfo>> entry : this.routeGroupMap.entrySet()) {
			String groupName = entry.getKey();

			/*
			 public void loadRoute(Map<String, RouteMeta> routeMetaMap)
			*/
			MethodSpec.Builder loadRouteMetaMethod = MethodSpec.methodBuilder(Const.METHOD_LOAD_ROUTE)
					.addAnnotation(Override.class)
					.addModifiers(PUBLIC)
					.addParameter(routeMetaMapParameter);
			
			for (RouteInfo routeInfo : entry.getValue()) {
				/*
				 routeMetaMap.put("app/activity/second", RouteMeta.build(RouteType.ACTIVITY, "app/activity/second", SecondActivity.class));
				*/
				loadRouteMetaMethod.addStatement(
						String.format(Locale.CHINA, Const.METHOD_LOAD_ROUTE_META_STATEMENT, Const.METHOD_LOAD_ROUTE_META_PARAMETER, routeInfo.getRouteType()),
						routeInfo.getRoutePath(),
						routeMetaClassName,
						routeTypeClassName,
						routeInfo.getRoutePath(),
						ClassName.get((TypeElement) routeInfo.getRouteElement()));
			}
			
			String javaFileName = captureName(this.moduleName) + Const.SEPARATOR + captureName(groupName) + Const.ROUTE_GROUP;
			JavaFile.builder(Const.PACKAGE_IMPLEMENT,
					TypeSpec.classBuilder(javaFileName)
							.addModifiers(PUBLIC)
							.addSuperinterface(ClassName.get(iRouteGroup))
							.addMethod(loadRouteMetaMethod.build())
							.build())
					.build()
					.writeTo(this.filer);
			
			this.routeModuleMap.put(groupName, javaFileName);
		}
		
		for (Map.Entry<String, String> entry : this.routeModuleMap.entrySet()) {
			loadRouteGroupMethod.addStatement(Const.METHOD_LOAD_ROUTE_GROUP_STATEMENT, entry.getKey(), ClassName.get(Const.PACKAGE_IMPLEMENT, entry.getValue()));
		}
		
		String moduleFileName = captureName(this.moduleName) + Const.SEPARATOR + Const.ROUTE_MODULE;
		JavaFile.builder(Const.PACKAGE_IMPLEMENT,
				TypeSpec.classBuilder(moduleFileName)
						.addSuperinterface(ClassName.get(elements.getTypeElement(Const.TYPE_I_ROUTE_MODULE)))
						.addModifiers(PUBLIC)
						.addMethod(loadRouteGroupMethod.build())
						.build()
		).build().writeTo(this.filer);
	}
	
	private void categories(RouteInfo routeInfo) {
		if (!verifyInfo(routeInfo)) {
			this.logger.info("routeMeta routePath error: " + routeInfo.getRoutePath());
			return;
		}
		
		Set<RouteInfo> routeInfoSet = routeGroupMap.get(routeInfo.getGroupName());
		if (routeInfoSet == null) {
			routeInfoSet = new TreeSet<>(new Comparator<RouteInfo>() {
				@Override
				public int compare(RouteInfo routeInfo1, RouteInfo routeInfo2) {
					try {
						return routeInfo1.getRoutePath().compareTo(routeInfo2.getRoutePath());
					} catch (Exception e) {
						logger.error(e.getMessage());
						return 0;
					}
				}
			});
			this.routeGroupMap.put(routeInfo.getGroupName(), routeInfoSet);
		}
		routeInfoSet.add(routeInfo);
	}
	
	private boolean verifyInfo(RouteInfo routeInfo) {
		String routePath = routeInfo.getRoutePath();
		if (routePath == null || routePath.isEmpty()) {
			return false;
		}
		
		String groupName = routePath.substring(0, routePath.indexOf("/", 0));
		if (groupName.isEmpty()) {
			return false;
		}
		routeInfo.setGroupName(groupName);
		
		return true;
	}
	
	private static String captureName(String name) {
		char[] charArray = name.toCharArray();
		charArray[0] -= 32;
		return String.valueOf(charArray);
	}
}
