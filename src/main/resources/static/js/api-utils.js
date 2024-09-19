// api-utils.js

const API_BASE_URL = 'http://localhost:8080/api';  // Set your API base URL

/**
 * Performs a fetch request with automatic error handling and retries.
 * @param {string} endpoint - The API endpoint (relative to the base URL).
 * @param {object} options - The fetch options (method, headers, body, etc.).
 * @param {number} retries - The number of times to retry the request in case of failure.
 * @returns {Promise<any>} - A promise resolving with the response data or rejecting with an error.
 */
async function apiRequest(endpoint, options = {}, retries = 0) {
    const url = `${API_BASE_URL}${endpoint}`;

    try {
        const response = await fetch(url, {
            credentials: "include",
            ...options
        });
        /////
        console.log(`fetch url: ${url}`)
        console.log(response.status)

        //        if (response.redirected){
        //                window.location.href = response.url;
        //        }
        if (response.ok) {
            // Handle JSON or text responses
            const contentType = response.headers.get('content-type');
            return contentType && contentType.includes('application/json')
                ? await response.json()
                : await response.text();
        } else {
            // Handle error response statuses
            return await handleErrorResponse(response);
        }
    } catch (error) {
        if (retries > 0) {
            console.warn(`Request failed, retrying... (${retries} retries left)`);
            return apiRequest(endpoint, options, retries - 1);  // Retry on failure
        } else {
            console.error('API request failed after retries:', error);
            showToast('A network error occurred. Please try again later.');
            throw error;
        }
    }
}

/**
 * Handle error responses based on status codes and show appropriate user feedback.
 * @param {Response} response - The fetch response object.
 * @returns {Promise<Error>} - A rejected promise to handle the error in calling code.
 */
async function handleErrorResponse(response) {
    // if (response.status === 404) {
    //     showToast(`Resource not found.
    //         Server message: ${response.text()}`);
    // } else if (response.status === 500) {
    //     showToast('Internal server error. Please try again later.');
    // } 
    // else {
    //     showToast(`An error occurred: ${response.statusText} (Status: ${response.status})`);
    // }

    const serverMessage = await response.text();
    showToast(`An error occurred: ${response.statusText} (Status: ${response.status})
Server messgae: ${serverMessage}`);
    return response;
    // throw new Error(`Request failed with status ${response.status}: ${response.statusText}`);
}

/**
 * Shows a toast notification to the user (customizable UI for user feedback).
 * @param {string} message - The message to display to the user.
 */
function showToast(message) {
    const toastElement = document.getElementById('toast');
    if (toastElement) {
        toastElement.innerText = message;
        toastElement.classList.add('show');
        setTimeout(() => toastElement.classList.remove('show'), 3000);  // Auto-hide after 3 seconds
    } else {
        alert(message);  // Fallback if no toast element exists
    }
}

/**
 * Convenience function for making a GET request.
 * @param {string} endpoint - The API endpoint (relative to base URL).
 * @param {object} options - Additional options (headers, query params, etc.).
 * @returns {Promise<any>} - A promise resolving with the response data.
 */
async function apiGet(endpoint, options = {}) {
    return await apiRequest(endpoint, { method: 'GET', ...options });
}

/**
 * Convenience function for making a POST request.
 * @param {string} endpoint - The API endpoint (relative to base URL).
 * @param {object} data - The data to send in the request body.
 * @param {object} options - Additional options (headers, etc.).
 * @returns {Promise<any>} - A promise resolving with the response data.
 */
async function apiPost(endpoint, data, options = {}) {
    console.log('apiPost called')
    const responseBody = await apiRequest(endpoint, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json', ...options.headers },
        body: JSON.stringify(data),
        ...options
    });
    //For confirmation message
    showToast(responseBody)
}

/**
 * Convenience function for making a PUT request.
 * @param {string} endpoint - The API endpoint (relative to base URL).
 * @param {object} data - The data to send in the request body.
 * @param {object} options - Additional options (headers, etc.).
 * @returns {Promise<any>} - A promise resolving with the response data.
 */
async function apiPut(endpoint, data = {}, options = {}) {
    const response = await apiRequest(endpoint, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json', ...options.headers },
        body: JSON.stringify(data),
        ...options
    });
    showToast(response)
}

/**
 * Convenience function for making a DELETE request.
 * @param {string} endpoint - The API endpoint (relative to base URL).
 * @param {object} options - Additional options (headers, etc.).
 * @returns {Promise<any>} - A promise resolving with the response data.
 */
async function apiDelete(endpoint, options = {}) {
    const response = await apiRequest(endpoint, { method: 'DELETE', ...options });
    showToast(response)
}
