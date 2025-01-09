package com.gs24.website.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gs24.website.domain.QuestionAttach;

@Mapper
public interface QuestionAttachMapper {
	int insertQuestionAttach(QuestionAttach questionAttach);
    List<QuestionAttach> selectByQuestionId(int questionId);
    QuestionAttach selectByQuestionAttachId(int questionAttachId);
    int insertQuestionAttachModify(QuestionAttach questionAttach);
    int deleteQuestionAttach(int questionId);
    List<QuestionAttach> selectOldList();
}
