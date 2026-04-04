package com.nakao.maneta.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArticleRequest {
    private String title;
    private String body;
    private boolean published;
}
