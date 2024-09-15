package com.finki.journalingapplication.service;

import com.finki.journalingapplication.model.Diary;
import com.finki.journalingapplication.model.User;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface DiaryService {

        List<Diary> findAll();

        Diary findById(Long id);

        Diary create(String content,String title, LocalDate date,Long userId);


        Diary delete(Long id);

        Diary edit(Long id, String content,String title, Long user, LocalDate date);

        List<Diary> findByUserId(Long userId);

        List<Diary> findByUserIdAndDate(Long userId, LocalDate date);



}
