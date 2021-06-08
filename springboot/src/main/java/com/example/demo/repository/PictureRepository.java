package com.example.demo.repository;

import com.example.demo.bean.PictureInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PictureRepository extends JpaRepository<PictureInfo, Integer> {
    PictureInfo findByPicId(Integer picId);
}
