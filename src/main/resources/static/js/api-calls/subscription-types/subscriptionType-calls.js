const subTypeBaseEndPoint = "/sub-type"

function appendToSubTypeEndPoint(extension) {
    appendToGenericEndPoint(subTypeBaseEndPoint, extension)
}

//Sub types will be displayed in dropdown menu
//So no need for find by id 
async function getAllSubscriptionTypes() {
    return await apiGet(subTypeBaseEndPoint)
}

async function saveSubscriptionType(subscriptionType) {
    const endpoint = appendToSubTypeEndPoint("/admin/save")
    await apiPost(endpoint, subscriptionType)
}

// async function deleteSubscriptionTypeById(subscriptionTypeId) {
//     await apiDelete(`${subTypeBaseEndPoint}/${subscriptionTypeId}`)
// }