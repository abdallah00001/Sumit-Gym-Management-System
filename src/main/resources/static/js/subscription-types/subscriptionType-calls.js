const subTypeBaseEndPoint = "/admin/sub-type"

//Sub types will be displayed in dropdown menu
//So no need for find by id 
async function getAllSubscriptionTypes() {
    return await apiGet(subTypeBaseEndPoint)
}

async function saveSubscriptionType(subscriptionType) {
    await apiPost(subTypeBaseEndPoint, subscriptionType)
}

async function deleteSubscriptionTypeById(subscriptionTypeId) {
    await apiDelete(`${subTypeBaseEndPoint}/${subscriptionTypeId}`)
}