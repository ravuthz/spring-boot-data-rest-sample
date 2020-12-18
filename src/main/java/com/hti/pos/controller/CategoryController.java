package com.hti.pos.controller;


import com.hti.pos.dto.response.ApiSuccess;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/categories")
public class CategoryController {

    @GetMapping
    public ApiSuccess index() {
        return new ApiSuccess(HttpStatus.OK, "success");
    }

}
