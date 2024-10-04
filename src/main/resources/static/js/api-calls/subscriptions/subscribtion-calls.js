const subscirptionEndPoint = "/sub"

function appendSubToBaseEndPoint(extension){
    return appendToGenericEndPoint(subscirptionEndPoint, extension)
}

async function getAllSubs() {
    const response = await apiGet(subscirptionEndPoint)
    return response;
}

async function findSubsByUser(userName) {
    const endpoint = appendSubToBaseEndPoint(`/user/${userName}`) 
    const response = await apiGet(endpoint)
    return response;
}

//finds subs created between 2 dates 
//enter same day as start and finish for 1 day search results
async function findSubsByCreationDateRange(startDate, finishDate) {
    const endpoint = appendSubToBaseEndPoint(`/${startDate}/${finishDate}`)
    const response = await apiGet(endpoint)
    return response;
}

async function findSubByMemberId(memberId) {
    const endpoint = appendSubToBaseEndPoint(`/member/${memberId}`)
    const response = await apiGet(endpoint)
    return response;
}

//ADMIN ACCESS ONLY
//Get total income between 2 dates 
async function findIncomeInDateRange(startDate,finishDate) {
    const endpoint = appendSubToBaseEndPoint(`/admin/income/${startDate}/${finishDate}`)
    const response = await apiGet(endpoint)
    return response
}


async function saveSub(subscription, subscriptionTypeId, paidTotal) {

    const endpoint = appendSubToBaseEndPoint(`/sub-type/${subscriptionTypeId}/cashout/${paidTotal}`)
    await apiPost(endpoint, subscription)
}

async function addAteendanceToSub(memberId, date) {
    //If date not provided add today
    if(!date){
        date = new Date().toLocaleDateString("en-CA");
    }

    const endpoint = appendSubToBaseEndPoint(`/member/${memberId}/day/${date}`)
    await apiPut(endpoint)
}
