package com.green.greengram.application.feed.model;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
@Setter
public class FeedPostReq {
    @Size(max = 1000)
    private String contents;

    @Size(max = 30)
    private String location;
}
