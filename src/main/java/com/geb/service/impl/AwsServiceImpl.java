package com.geb.service.impl;

import java.io.InputStream;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.geb.handler.exception.InternalErrorException;
import com.geb.service.IAwsService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AwsServiceImpl implements IAwsService {
	
	@Autowired
	private AmazonS3 s3client;

	@Value("${aws.s3.bucket}")
	private String bucketName;

	@Override
	public void uploadFile(InputStream is, String fileName, String contentType, String folderName) {
		try {
			String bucket = StringUtils.isBlank(folderName) ? bucketName : bucketName.concat("/").concat(folderName);
			
			ObjectMetadata meta = new ObjectMetadata();
			meta.setContentType(contentType);
			log.info("Iniciando upload");
			s3client.putObject(bucket, fileName, is, meta);
			log.info("Upload finalizado");
		} catch (Exception e) {
			throw new InternalErrorException(e.getLocalizedMessage());
		}
	}
}
