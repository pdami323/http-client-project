package com.http.client.httpclientproject.restTemplate.dao;

import com.http.client.httpclientproject.restTemplate.dto.request.UserRequestDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface RestTemplateDAO {

    void createUser(UserRequestDTO userRequestDTO);
}
