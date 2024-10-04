const coachEndPoint = '/coach'

function appendToCoachEndPoint(extension) {
    appendToGenericEndPoint(coachEndPoint, extension)
}

async function getAllCoaches() {
    return await apiGet(coachEndPoint)
}

async function saveCoach(coach) {
    apiPost(coachEndPoint, coach)
}