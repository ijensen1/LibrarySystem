public class AccountCurrentBorrowStatus {

    private String barrowStatus;
    private String barrowBranch;

    public AccountCurrentBorrowStatus(){

        this("in", "National Branch");
    }

    public AccountCurrentBorrowStatus(String barrowStatus, String barrowBranch){
        this.barrowStatus = barrowStatus;
        this.barrowBranch = barrowBranch;
    }

    public String getBarrowStatus(){

        return barrowStatus;

    }

    public String getBarrowBranch(){

        return barrowBranch;

    }


    public void setBarrowStatus(){

        this.barrowStatus = barrowStatus;

    }

    public void setBarrowBranch(){

        this.barrowBranch = barrowBranch;

    }

}
