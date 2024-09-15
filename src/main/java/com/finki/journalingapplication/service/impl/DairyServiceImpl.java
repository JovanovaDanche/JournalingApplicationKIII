package com.finki.journalingapplication.service.impl;


import com.finki.journalingapplication.model.Diary;
import com.finki.journalingapplication.model.User;
import com.finki.journalingapplication.model.exceptions.InvalidDiaryIdException;
import com.finki.journalingapplication.model.exceptions.UserNotFoundException;
import com.finki.journalingapplication.repository.DiaryRepository;
import com.finki.journalingapplication.repository.UserRepository;
import com.finki.journalingapplication.service.DiaryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.security.InvalidAlgorithmParameterException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class DairyServiceImpl implements DiaryService {

    private final DiaryRepository diaryRepository;
    private final UserRepository userRepository;

    public DairyServiceImpl(DiaryRepository diaryRepository, UserRepository userRepository) {
        this.diaryRepository = diaryRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Diary> findAll() {
        return diaryRepository.findAll();
    }

    @Override
    public Diary findById(Long id) {
        return diaryRepository.findById(id).orElseThrow(InvalidDiaryIdException::new);
    }
    @Override
    public List<Diary> findByUserId(Long userId) {
        return diaryRepository.findByUserId(userId);
    }


    @Override
    public Diary create(String content,String title,LocalDate date,Long userId) {
        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));
        Diary diary = new Diary(content,title,date,user);
        return diaryRepository.save(diary);
    }
    @Override
    public Diary delete(Long id) {
        Diary diary = findById(id);
        diaryRepository.deleteById(id);
        return diary;

    }

    @Override
    public Diary edit(Long id, String content,String title, Long userId, LocalDate date) {
        Diary diary = findById(id);
        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        diary.setContent(content);
        diary.setTitle(title);
        diary.setDate(date);
        diary.setUser(user);

        return diaryRepository.save(diary);

    }

    @Override
    public List<Diary> findByUserIdAndDate(Long userId, LocalDate date) {
        return diaryRepository.findByUserIdAndDate(userId, date);
    }


}


