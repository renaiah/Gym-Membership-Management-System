
public class Member extends Person {
	private String memberId;
    private MembershipPlan plan;

    public Member(String memberId, String personName, int personAge) {
        super(personName, personAge);
        this.memberId = memberId;
    }

    public String getMemberId() 
    { 
    	return memberId; 
    }
    public MembershipPlan getPlan() 
    {
    	return plan; 
    }
    public void assignPlan(MembershipPlan plan) 
    { 
    	this.plan = plan; 
    }

    @Override
    public void showDetails() {
        System.out.println("--------------------------------------------------------------");
        System.out.println("Name : " + getName());
        System.out.println("Member ID : " + memberId);
        System.out.println("Age : " + getAge());
        if (plan != null) {
            System.out.println("Membership Plan: " + plan);
        } 
        else 
        {
            System.out.println("		No membership plan assigned to "+getName()+".Please Add membership plan...");
        }
        System.out.println("--------------------------------------------------------------");
    }
}
