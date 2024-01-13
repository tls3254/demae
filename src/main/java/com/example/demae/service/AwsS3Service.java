package com.example.demae.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.demae.entity.Picture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class AwsS3Service {
	@Value("${application.bucket.name}")
	private String bucketName;
	private final AmazonS3 s3Client;
	private final PictureRepository pictureRepository;
	private final ItemRepository itemRepository;

	@Transactional
	public void uploadFiles(List<MultipartFile> files, Long itemId) throws IOException {
		Item item = itemRepository.findById(itemId).orElseThrow();
		for (MultipartFile file : files) {
			File fileObj = convertMultiPartFileToFile(file);
			String fileName = UUID.randomUUID() + "." + extractExtension(file);
			pictureRepository.save(new Picture(item, fileName));
			s3Client.putObject(new PutObjectRequest(bucketName, fileName, fileObj));
			fileObj.delete();
		}
	}

	public List<String> getObjectUrlsForItem(Long itemId) {
		List<Picture> pictures = pictureRepository.findByItemId(itemId);
		List<String> objectUrls = new ArrayList<>();
		for (Picture picture : pictures) {
			String objectUrl = s3Client.getUrl(bucketName, picture.getUuid()).toString();
			objectUrls.add(objectUrl);
		}
		return objectUrls;
	}

	private File convertMultiPartFileToFile(MultipartFile file) throws IOException {
		File convertedFile = new File(file.getOriginalFilename());
		FileOutputStream fos = new FileOutputStream(convertedFile);
		fos.write(file.getBytes());
		return convertedFile;
	}

	private String extractExtension(MultipartFile file) {
		String[] filenameParts = file.getOriginalFilename().split("\\.");
		return filenameParts[filenameParts.length - 1];
	}
}
