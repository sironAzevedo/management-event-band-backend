package com.geb.service;

import java.io.InputStream;

public interface IAwsService {
	
	void uploadFile(InputStream is, String fileName, String contentType, String folderName);
}
