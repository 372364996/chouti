package com.chewawa.baselibrary.base.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.List;

/**
 * 分页结果
 * nanfeifei
 * 2017/6/21 14:07
 *
 * @version 3.7.0
 */
public class PageResultBean<T> implements Serializable{

  /**
   * dataList : [{"headImgUrl":"https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=1733071988,3600838707&fm=117&gp=0.jpg","theadName":"梁开东","status":"其他","statusColor":"","remark":"对比中还在考虑 考虑考虑","userId":50185},{"headImgUrl":"https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=1733071988,3600838707&fm=117&gp=0.jpg","theadName":"13825078163","status":"有点意向","statusColor":"#BB6C44","remark":"","userId":50154},{"headImgUrl":"https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=1733071988,3600838707&fm=117&gp=0.jpg","theadName":"18671021980","status":"待成交","statusColor":"#BB5C44","remark":"对比中还在考虑 考虑考虑","userId":50155},{"headImgUrl":"https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=1733071988,3600838707&fm=117&gp=0.jpg","theadName":"18339465206","status":"待成交","statusColor":"#BB5C44","remark":"对比中还在考虑 考虑考虑","userId":50156},{"headImgUrl":"http://wx.qlogo.cn/mmopen/ajNVdqHZLLCeVZ9NIL8vBibuZYwv0BCrkRSUu4Bbjqol8tMWOhU54DxseM2SIHKbD8cF7BTeE85gKBo2yOu819Q/0","theadName":"13375197055","status":"待成交","statusColor":"#BB5C44","remark":"对比中还在考虑 考虑考虑","userId":50157},{"headImgUrl":"https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=1733071988,3600838707&fm=117&gp=0.jpg","theadName":"15629799101","status":"待成交","statusColor":"#BB5C44","remark":"对比中还在考虑 考虑考虑","userId":50158},{"headImgUrl":"https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=1733071988,3600838707&fm=117&gp=0.jpg","theadName":"18577161551","status":"待成交","statusColor":"#BB5C44","remark":"对比中还在考虑 考虑考虑","userId":50159},{"headImgUrl":"https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=1733071988,3600838707&fm=117&gp=0.jpg","theadName":"18603518081","status":"可详聊","statusColor":"#BB6C44","remark":"对比中还在考虑 考虑考虑","userId":50161},{"headImgUrl":"https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=1733071988,3600838707&fm=117&gp=0.jpg","theadName":"15964407377","status":"可详聊","statusColor":"#BB6C44","remark":"对比中还在考虑 考虑考虑","userId":50162},{"headImgUrl":"https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=1733071988,3600838707&fm=117&gp=0.jpg","theadName":"13793517372","status":"可详聊","statusColor":"#BB6C44","remark":"对比中还在考虑 考虑考虑","userId":50164}]
   * totalCount : 42
   * pageTotalNumber : null
   * pageNumber : 10
   * pageIndex : 1
   */
  @JSONField(name = "TotalCount")
  public int totalCount;
  @JSONField(name = "PageTotalNumber")
  public int pageTotalNumber;
  @JSONField(name = "PageNumber")
  public int pageNumber;
  @JSONField(name = "PageIndex")
  public int pageIndex;
  @JSONField(name = "DataList")
  public List<T> dataList;
  @JSONField(name = "List")
  public List<T> list;  //兼容延保订单列表接口，如果接口更改为上面的统一分页信息形式，可删除

  public int getTotalCount() {
    return totalCount;
  }

  public void setTotalCount(int totalCount) {
    this.totalCount = totalCount;
  }

  public int getPageTotalNumber() {
    return pageTotalNumber;
  }

  public void setPageTotalNumber(int pageTotalNumber) {
    this.pageTotalNumber = pageTotalNumber;
  }

  public int getPageNumber() {
    return pageNumber;
  }

  public void setPageNumber(int pageNumber) {
    this.pageNumber = pageNumber;
  }

  public int getPageIndex() {
    return pageIndex;
  }

  public void setPageIndex(int pageIndex) {
    this.pageIndex = pageIndex;
  }

  public List<T> getDataList() {
    if(dataList == null){
      dataList = list;
    }
    return dataList;
  }

  public void setDataList(List<T> dataList) {
    this.dataList = dataList;
  }

  public List<T> getList() {
    return list;
  }

  public void setList(List<T> list) {
    this.list = list;
  }
}
