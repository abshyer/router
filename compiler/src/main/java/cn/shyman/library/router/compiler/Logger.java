package cn.shyman.library.router.compiler;

import javax.annotation.processing.Messager;
import javax.tools.Diagnostic;

class Logger {
	private Messager messager;
	
	Logger(Messager messager) {
		this.messager = messager;
	}
	
	void info(String message) {
		this.messager.printMessage(Diagnostic.Kind.WARNING, message);
	}
	
	void error(String message) {
		this.messager.printMessage(Diagnostic.Kind.ERROR, message);
	}
}
