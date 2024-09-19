const subscirptionEndPoint = "/sub"

function appendToBaseEndPoint(extention){
    return `${subscirptionEndPoint}${extention}`
}

async function getAllSubs() {
    const response = await apiGet(subscirptionEndPoint)
    return response;
}

async function findSubsByUser(userName) {
    const endpoint = appendToBaseEndPoint(`/user/${userName}`) 
    const response = await apiGet(endpoint)
    return response;
}

//finds subs created between 2 dates 
//enter same day as start and finish for 1 day search results
async function findSubsByCreationDateRange(startDate, finishDate) {
    const endpoint = appendToBaseEndPoint(`/${startDate}/${finishDate}`)
    const response = await apiGet(endpoint)
    return response;
}

async function findSubByMemberId(memberId) {
    const endpoint = appendToBaseEndPoint(`/member/${memberId}`)
    const response = await apiGet(endpoint)
    return response;
}

//ADMIN ACCESS ONLY
//Get total income between 2 dates 
async function findIncomeInDateRange(startDate,finishDate) {
    const endpoint = appendToBaseEndPoint(`/admin/income/${startDate}/${finishDate}`)
    const response = await apiGet(endpoint)
    return response
}

//Request body as subscription with only type data inserted 
//Path variable as memberId
async function saveSub(memberId, subscriptionType) {

    const subscription = {
        subscriptionType: subscriptionType
    }

    const endpoint = appendToBaseEndPoint(`/member/${memberId}`)
    await apiPost(endpoint, subscription)
}

async function addAteendanceToSub(memberId, date) {
    //If date not provided add today
    if(!date){
        date = new Date().toLocaleDateString("en-CA");
    }

    const endpoint = appendToBaseEndPoint(`/member/${memberId}/day/${date}`)
    await apiPut(endpoint)
}
