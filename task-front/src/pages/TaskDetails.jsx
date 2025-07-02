import { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import axios from '../utils/axiosInstance';

const TaskDetails = () => {
    const { id } = useParams();
    const navigate = useNavigate();
    const [task, setTask] = useState(null);

    useEffect(() => {
        axios.get(`/tasks/${id}`).then((res) => setTask(res.data));
    }, [id]);

    const handleDelete = async () => {
        if (window.confirm('Are you sure you want to delete this task?')) {
            await axios.delete(`/tasks/${id}`);
            navigate('/dashboard');
        }
    };

    if (!task) return <p className="text-center text-gray-500 mt-10">Loading...</p>;

    return (
        <div className="min-h-screen bg-white dark:bg-gray-900 text-gray-900 dark:text-white py-10 px-4">
            <div className="max-w-2xl mx-auto bg-white dark:bg-gray-800 p-6 rounded-lg shadow">
                <div className="mb-6">
                    <h1 className="text-3xl font-bold text-blue-600 dark:text-blue-400">{task.title}</h1>
                    <p className="text-gray-500 dark:text-gray-400 mt-2">{task.description}</p>
                </div>

                <div className="grid grid-cols-1 sm:grid-cols-2 gap-4 text-sm">
                    <div className="p-3 bg-gray-100 dark:bg-gray-700 rounded">
                        <p className="text-gray-500">Status</p>
                        <p className="font-semibold">{task.status}</p>
                    </div>
                    <div className="p-3 bg-gray-100 dark:bg-gray-700 rounded">
                        <p className="text-gray-500">Priority</p>
                        <p className="font-semibold">{task.priority}</p>
                    </div>
                    <div className="p-3 bg-gray-100 dark:bg-gray-700 rounded">
                        <p className="text-gray-500">Due Date</p>
                        <p className="font-semibold">{new Date(task.dueDate).toLocaleDateString()}</p>
                    </div>
                    {/* Add more fields here if needed */}
                </div>

                <div className="mt-6 flex justify-end gap-3">
                    <button
                        onClick={() => navigate(`/task/edit/${task.id}`)}
                        className="px-4 py-2 bg-gray-600 hover:bg-gray-700 text-white rounded"
                    >
                        Edit
                    </button>
                    <button
                        onClick={handleDelete}
                        className="px-4 py-2 bg-red-600 hover:bg-red-700 text-white rounded"
                    >
                        Delete
                    </button>
                </div>
            </div>
        </div>
    );
};

export default TaskDetails;
