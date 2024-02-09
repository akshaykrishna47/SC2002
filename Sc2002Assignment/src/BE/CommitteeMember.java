package BE;

import java.util.ArrayList;

/**
 * Represents a Committee Member, extending the functionality of a Student.
 * Committee members are individuals participating in specific contexts as part of a committee.
 */
public class CommitteeMember extends Student {
    
    /**
     * Constructs a Committee Member with the provided details.
     *
     * @param name         The name of the committee member.
     * @param ntuNetworkId The NTU network ID of the committee member.
     * @param faculty      The faculty of the committee member.
     */
	public CommitteeMember(String name, String ntuNetworkId, String faculty) {
        super(name, ntuNetworkId, faculty);
        new ArrayList<>();
    }

}

