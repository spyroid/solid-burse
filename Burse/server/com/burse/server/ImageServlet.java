package com.burse.server;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.images.Image;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.Transform;

public class ImageServlet extends HttpServlet {

	private static final long serialVersionUID = 2207336276116094526L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		byte[] imageData = getData(req);
		resp.setContentLength(imageData.length);
		resp.setContentType("image/png");
		ServletOutputStream outputStream = resp.getOutputStream();
		outputStream.write(imageData);
		outputStream.close();
		resp.flushBuffer();

	}

	private byte[] getData(HttpServletRequest req) throws IOException {
		InputStream resourceAsStream = getClass().getResourceAsStream("/com/burse/server/NoImage.png");
		byte[] data = new byte[resourceAsStream.available()];
		resourceAsStream.read(data, 0, data.length);
		String newHeight = req.getParameter("height");
		if (newHeight != null) {
			Image makeImage = ImagesServiceFactory.makeImage(data);
			ImagesService imagesService = ImagesServiceFactory.getImagesService();
			Transform makeResize = ImagesServiceFactory.makeResize(100, 100);
			Image applyTransform = imagesService.applyTransform(makeResize, makeImage);
			return applyTransform.getImageData();
			
		}

		return data;
	}

}
