const membersendpoint = "/members"

function appendToMemberEndPoint(extension) {
    return appendToGenericEndPoint(membersendpoint, extension)
}

async function getAllMembers() {
    const response = await apiGet(membersendpoint)
    return response;
}

async function findMemberById(memberId) {
    const endpoint = appendToMemberEndPoint(memberId)
    const response = await apiGet(endpoint)
    return response;
}

async function saveMember(memberData) {
    await apiPost(membersendpoint, memberData)
}

async function updateMember(oldMemberId, updatedMemberData) {
    const endpoint = appendToMemberEndPoint(oldMemberId)
    await apiPut(endpoint, updatedMemberData)
}

async function deleteMember(memberId) {
    const endpoint = appendToMemberEndPoint(memberId)
    await apiDelete(endpoint)
}

