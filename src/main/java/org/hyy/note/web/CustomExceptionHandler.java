package org.hyy.note.web;

import java.io.IOException;

import org.hyy.note.exception.UnacceptableFileFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;

/**
 * 控制器异常控制
 * 捕捉一个异常, log并发送信息作为 @ResponseBody
 * 
 * @author huyanyan
 */
@ControllerAdvice
public class CustomExceptionHandler {

	private static final Logger LOG = LoggerFactory.getLogger(CustomExceptionHandler.class);
	
	@Autowired
	private ApplicationContext context;

	/**
	 * 控制 {@link MaxUploadSizeExceededException}
	 * 图片大小超出要求
	 */
	@ExceptionHandler(MaxUploadSizeExceededException.class)
	@ResponseBody
	public String handleMaxUploadSizeException(MaxUploadSizeExceededException e) {

		LOG.error(e.getMessage(), e);
		String message = context.getMessage("validation.file.maxSize", null,
				LocaleContextHolder.getLocale());
		return message;
	}

	/**
	 * 控制 {@link UnacceptableFileFormatException}
	 * 图片格式不对
	 */
	@ExceptionHandler(UnacceptableFileFormatException.class)
	@ResponseBody
	public String handleFileFormatException(UnacceptableFileFormatException e) {

		LOG.error(e.getMessage(), e);
		String message = context.getMessage("validation.file.type", null,
				LocaleContextHolder.getLocale());
		return message;
	}

	/**
	 * 控制 {@link IOException}, {@link MultipartException}
	 * 在文件上传过程中文件系统异常
	 */
	@ExceptionHandler(value = {IOException.class, MultipartException.class})
	@ResponseBody
	public String handleCustomException(Exception e) {

		LOG.error(e.getMessage(), e);
		String message = context.getMessage("validation.file.custom", null,
				LocaleContextHolder.getLocale());
		return message;
	}
}
