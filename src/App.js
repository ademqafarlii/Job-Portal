import React, { useEffect, useState } from 'react';
import axios from 'axios';

function App() {
    const [jobs, setJobs] = useState([]);

    useEffect(() => {
        axios.get('http://localhost:8080/api/jobs')  // Backend API endpoint
            .then(response => {
                setJobs(response.data);  // Backend-dən gələn məlumatları set et
            })
            .catch(error => {
                console.error('There was an error fetching the jobs!', error);
            });
    }, []);

    return (
        <div className="App">
            <h1>Job Portal</h1>
            <ul>
                {jobs.map(job => (
                    <li key={job.id}>
                        {job.jobTittle} at {job.companyName}
                    </li>
                ))}
            </ul>
        </div>
    );
}

export default App;
