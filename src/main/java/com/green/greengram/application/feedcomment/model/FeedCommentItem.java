package com.green.greengram.application.feedcomment.model;

import lombok.Getter;

@Getter
public class FeedCommentItem {
    private long feedCommentId;
    private String comment;
    private long writerUserid;
    private String writerUid;
    private String writerNickname;
    private String writerPic;

}
