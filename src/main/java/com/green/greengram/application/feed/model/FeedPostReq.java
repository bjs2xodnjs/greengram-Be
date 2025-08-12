package com.green.greengram.application.feed.model;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
@Setter
public class FeedPostReq {
    @Size(max = 1_000, message = "contents는 1,000자 이하여야 합니다.")
    private String contents;

    @Size(max = 30, message = "location은 30 이하여야 합니다.")
    private String location;
}
