package com.yier.platform.common.po;

import com.google.common.collect.Lists;
import com.yier.platform.common.jsonResponse.BaseJsonObject;

import java.util.List;

public class PracticePointList   extends BaseJsonObject {
    private List<PracticePoint> practicePointList = Lists.newArrayList();

    public List<PracticePoint> getPracticePointList() {
        return practicePointList;
    }

    public void setPracticePointList(List<PracticePoint> practicePointList) {
        this.practicePointList = practicePointList;
    }
}
