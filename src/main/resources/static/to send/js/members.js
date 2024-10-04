
const saveMemberEndPoint = "/members"
let currentMember = null

async function findMemberById(params) {
    return member ={
       "id": 1,
  "name": "John Doe",
  "phone": "123-456-7890",
  "gender": "MALE",
  "subscriptionForMemberDto": {
    "id": 1001,
    "typeSummaryDto": {
      "id": 501,
      "name": "General 6 months"
    },
    "attendedPeriod": "2 months, 15 days",
    "remainingPeriod": "3 months, 15 days",
    "startDate": "2024-01-01",
    "finishDate": "2024-04-01"
        }
    }
}
// document.getElementById('memberForm').addEventListener('submit', function(event) {
//     event.preventDefault();
    
//     console.log("submit clicked")
//     // Clear previous errors
//     document.getElementById('nameError').textContent = '';
//     document.getElementById('phoneError').textContent = '';

//     const nameElement = document.getElementById('name');
//     const phoneElement = document.getElementById('phone');
//     const name = nameElement.value.trim();
//     const phone = phoneElement.value.trim();
//     phoneElement.value = '';
//     nameElement.value = '';
    
//     const memberData = {
//         name: name,
//         phone: phone
//     }

//     saveMember(memberData)

// });


// Toggle form visibility
const formContainer = document.getElementById('formContainer');
let isFormVisible = false; // Track form visibility state

// Handle member search
const searchMemberButton = document.getElementById('searchMemberButton');
const memberInfoDiv = document.getElementById('memberInfo');

searchMemberButton.addEventListener('click', async function() {
    const memberId = document.getElementById('memberId').value;

    if (!memberId) {
        alert('Please enter a member ID');
        return;
    }

    try {
        // Replace with the actual API endpoint for fetching a member by ID
        // const response = await apiGet(`/members/${memberId}`);

        const response = await findMemberById(memberId)
        console.log(response)
        // Assuming response contains: { name, phone, attendedDays, remainingDaysCount }
        currentMember = response;

        // Display member details
        memberInfoDiv.innerHTML = `
            <h3>Member Details</h3>
            <span><strong> ${memberToString(currentMember)}</strong></span>
            `;
            // <p><strong>Name:</strong> ${currentMember.name}</p>
            // <p><strong>Phone:</strong> ${currentMember.phone}</p>
            // <p><strong>Attended Days:</strong> ${currentMember.attendedDays.join(', ')}</p>
            // <p><strong>Remaining Days:</strong> ${currentMember.remainingDaysCount}</p>
        } catch (error) {
        alert('Failed to fetch member. Please try again.');
        console.error(error);
    }
});

// Handle Add Session (can be extended later)
const addSessionButton = document.getElementById('addSessionButton');

addSessionButton.addEventListener('click', function() {

    if (!currentMember) {
        alert("No member selected")
        return;
    }

    const memberId = currentMember.id

    addAteendanceToSub(memberId)

    // alert('Add session functionality not yet implemented.');
});

function memberToString(member) {
    let sb = [];

    sb.push("Member Details:\n");
    sb.push("ID: " + (member.id || "N/A") + "\n");
    sb.push("Name: " + (member.name || "N/A") + "\n");
    sb.push("Phone: " + (member.phone || "N/A") + "\n");
    sb.push("Gender: " + (member.gender || "N/A") + "\n");
    sb.push("Remaining Days: " + (member.remainingDaysCount || "N/A") + "\n");

    sb.push("\nLatest Subscription Details:\n");
    if (member.latestSubscriptionDto) {
        let subscription = member.latestSubscriptionDto;
        
        sb.push("Subscription ID: " + (subscription.id || "N/A") + "\n");
        sb.push("Payment Type: " + (subscription.paymentType || "N/A") + "\n");

        if (subscription.userDto) {
            sb.push("User ID: " + (subscription.userDto.id || "N/A") + "\n");
            sb.push("User Name: " + (subscription.userDto.userName || "N/A") + "\n");
            sb.push("User Role: " + (subscription.userDto.role || "N/A") + "\n");
        }

        if (subscription.subscriptionType) {
            sb.push("Subscription Type: " + (subscription.subscriptionType.name || "N/A") + "\n");
            sb.push("General Price: " + (subscription.subscriptionType.generalPrice || "N/A") + "\n");
            sb.push("Private Trainer Price: " + (subscription.subscriptionType.privateTrainerPrice || "N/A") + "\n");
            sb.push("Duration in Days: " + (subscription.subscriptionType.durationInDays || "N/A") + "\n");
            sb.push("Allowed Freeze Days: " + (subscription.subscriptionType.allowedFreezeDays || "N/A") + "\n");
        }

        sb.push("Created At: " + (subscription.createdAt || "N/A") + "\n");
        sb.push("Start Date: " + (subscription.startDate || "N/A") + "\n");
        sb.push("Expire Date: " + (subscription.expireDate || "N/A") + "\n");
        sb.push("Attended Days: " + (subscription.attendedDays.length > 0 ? subscription.attendedDays.join(", ") : "None") + "\n");
        sb.push("Discount: " + (subscription.discount || 0) + "\n");
        sb.push("Final Price: " + (subscription.finalPrice || "N/A") + "\n");
        sb.push("Frozen: " + (subscription.frozen ? "Yes" : "No") + "\n");
        sb.push("Expired: " + (subscription.expired ? "Yes" : "No") + "\n");
    } else {
        sb.push("No Subscription Data Available\n");
    }

    return sb.join('');
}


// last update with chat gpt
// function displayUserData(user) {
//     // Fill in user details
//     document.getElementById('userName').textContent = user.name;
//     document.getElementById('userPhone').textContent = user.phone;
//     document.getElementById('userGender').textContent = user.gender === "MALE" ? "Male" : "Female";

//     // Fill in subscription details
//     document.getElementById('subscriptionType').textContent = user.subscriptionForMemberDto.typeSummaryDto.name;
//     document.getElementById('startDate').textContent = user.subscriptionForMemberDto.startDate;
//     document.getElementById('endDate').textContent = user.subscriptionForMemberDto.finishDate;
//     document.getElementById('attendedPeriod').textContent = user.subscriptionForMemberDto.attendedPeriod;
//     document.getElementById('remainingPeriod').textContent = user.subscriptionForMemberDto.remainingPeriod;
// }

// // Call the function to display the data
// displayUserData(userData);

// -to-do:
// -add radius to search by phone or id and link it with search content
// -add onClick to submit btn 
// -resend the info after the update to show the submited sessions 
// 