package com.example.springcloud.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CommentResult<T> {

    private Integer code;
    private String message;
    private T data;

    public CommentResult(Integer code, String message) {
        this(code, message, null);
    }

}
