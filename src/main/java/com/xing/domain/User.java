package com.xing.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
// 实体类序列化在后面加上 implements Serializable
public class User {
    private String name;
    private String age;
}
