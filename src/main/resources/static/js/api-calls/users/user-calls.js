const usersBaseEndPoint = "/admin/users"
const cashierEndPoint = `${usersBaseEndPoint}/cashiers`
const adminEndPoint = `${usersBaseEndPoint}/admin`

// function appendToUsersEndPoint(extension) {
//     return `${usersBaseEndPoint}${extension}`
// }

async function findAllCashierAccounts() {
    const endpoint = cashierEndPoint
    const response = await apiGet(endpoint)
    return response
}

async function findCashierByusername(username) {
    const endpoint = `${cashierEndPoint}/${username}`
    const response = await apiGet(endpoint)
    return response
}

async function findAdmin() {
    const endpoint = adminEndPoint
    const response = await apiGet(endpoint)
    return response
}

async function saveNewCashier(cashier) {
    const endpoint = cashierEndPoint
    await apiPost(endpoint, cashier)
}

//updatedCashier {{{MUST}}} have all data populated 
//it will be saved as it is to the data base 
async function updateCashier(oldCashierUsername, updatedCashier) {
    const endpoint = `${cashierEndPoint}/${oldCashierUsername}`
    await apiPut(endpoint, updatedCashier)
}

//updatedCashier {{{MUST}}} have all data populated 
//it will be saved as it is to the data base 
async function updateAdmin(updatedAdmin) {
    const endpoint = adminEndPoint
    await apiPut(endpoint, updatedAdmin)
}

async function deleteCashierByUsername(username) {
    const endpoint = `${cashierEndPoint}/${username}`
    await apiDelete(endpoint)
}

