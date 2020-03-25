package aite.model;

public class OrderModel {
  public int oid;
  public int workerUID;
  public String workerName;
  public int requesterID;
  public String requesterName;
  public String title;
  public float price;
  public String location;
  public String description;
  public String status;
  public String createTime;
  public int workerReviewRatings = -1;
  public String workerReview = "";
  public String workerReviewTime;
  public int requesterReviewRatings = -1;
  public String requesterReview = "";
  public String requesterReviewTime;
}
