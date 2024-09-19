const membersendpoint = "/members"

async function getAllMembers() {
    const response = await apiGet(membersendpoint)
    return response;
}

async function findMemberById(memberId) {
    const endpoint = `${membersendpoint}/${memberId}`
    const response = await apiGet(endpoint)
    return response;
}

async function saveMember(memberData) {
    await apiPost(membersendpoint, memberData)
}

async function updateMember(oldMemberId, updatedMemberData) {
    const endpoint = `${membersendpoint}/${oldMemberId}`
    await apiPut(endpoint, updatedMemberData)
}

async function deleteMember(memberId) {
    const endpoint = `${membersendpoint}/${memberId}`
    await apiDelete(endpoint)
}

