package com.nakao.maneta.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ArticleRequest {
    private String title;
    private String body;
    private boolean published;
    private List<String> tagNames;
}
