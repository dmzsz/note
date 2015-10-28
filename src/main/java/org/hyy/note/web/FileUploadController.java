package org.hyy.note.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;

import javax.servlet.http.HttpServletResponse;

import org.hyy.note.exception.UnacceptableFileFormatException;
import org.hyy.note.web.constants.Common;
import org.hyy.note.web.constants.URL;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件上传
 * 
 * @author huyanyan
 */
@Controller
public class FileUploadController {

	/**
	 * Upload image submit
	 */
	@RequestMapping(method = RequestMethod.POST, value = URL.UPLOAD_IMAGE)
	@ResponseBody
	public String uploadimage(@RequestParam("file") MultipartFile image)
			throws IOException {

		String imageName = Calendar.getInstance().getTimeInMillis()
				+ image.getOriginalFilename();

		if (!image.isEmpty()) {
			String imageType = image.getContentType();
			if (!(imageType.equals(Common.JPG_CONTENT_TYPE) || imageType
					.equals(Common.PNG_CONTENT_TYPE))) {

				// TODO: 添加文件内容验证

				throw new UnacceptableFileFormatException();
			}

			File file = new File(Common.ARTICLE_IMAGES_PATH + imageName);
			FileUtils.writeByteArrayToFile(file, image.getBytes());
		}

		return "images/" + imageName;
	}

	/**
	 * 查看图片
	 * 
	 * @param imageName
	 *            image-name
	 * @param type
	 *            extension of image
	 * @param response
	 *            {@link HttpServletResponse}
	 * @throws IOException
	 */
	@RequestMapping(method = RequestMethod.GET, value = URL.SHOW_IMAGE)
	public void showImg(@PathVariable("name") String imageName,
			@PathVariable("type") String type, HttpServletResponse response)
			throws IOException {

		try (InputStream in = new FileInputStream(Common.ARTICLE_IMAGES_PATH
				+ imageName + "." + type)) {
			FileCopyUtils.copy(in, response.getOutputStream());
		}
	}
}