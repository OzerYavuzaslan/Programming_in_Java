package emlakcepte.client.model;

public class Payment {
    public Integer userId;
    public String cardNo;

public Payment(){}
    public Payment(Integer userId, String cardNo) {
        this.userId = userId;
        this.cardNo = cardNo;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }
}
