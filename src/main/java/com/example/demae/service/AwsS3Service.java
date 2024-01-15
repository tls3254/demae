package com.example.demae.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.demae.entity.Menu;
import com.example.demae.entity.Picture;
import com.example.demae.repository.MenuRepository;
import com.example.demae.repository.PictureRepository;
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
	private final MenuRepository menuRepository;

	@Transactional
	public void uploadFiles(List<MultipartFile> files, Long storeId) throws IOException {
		Menu menu = menuRepository.findById(storeId).orElseThrow();
		for (MultipartFile file : files) {
			File fileObj = convertMultiPartFileToFile(file);
			String fileName = UUID.randomUUID() + "." + extractExtension(file);
			pictureRepository.save(new Picture(menu, fileName));
			s3Client.putObject(new PutObjectRequest(bucketName, fileName, fileObj));
			fileObj.delete();
		}
	}

	public List<String> getObjectUrlsForMenu(Long menuId) {
		List<Picture> pictures = pictureRepository.findByMenuId(menuId);
		List<String> objectUrls = new ArrayList<>();
		for (Picture picture : pictures) {
			String objectUrl = s3Client.getUrl(bucketName, picture.getUuid()).toString();
			objectUrls.add(objectUrl);
		}
		return objectUrls;
	}
	@Transactional
	public void deleteFilesByMenuId(Long menuId) {
		// 데이터베이스에서 해당 menuId에 속한 모든 파일 정보 조회
		List<Picture> pictures = pictureRepository.findByMenuId(menuId);
		// 각 파일에 대해 S3에서 삭제하고 데이터베이스에서도 삭제
		for (Picture picture : pictures) {
			String uuid = picture.getUuid();
			s3Client.deleteObject(bucketName, uuid);
			pictureRepository.delete(picture);
		}
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
