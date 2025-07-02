import { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import axios from '../utils/axiosInstance';

const TaskForm = () => {
    const { id } = useParams();
    const navigate = useNavigate();

    const [form, setForm] = useState({
        title: '',
        description: '',
        deadline: '',
        priority: 'low',
        taskStats: 'todo',
        createdByUserId: '',
    });

    const [users, setUsers] = useState([]);
    const [error, setError] = useState('');

    useEffect(() => {
        axios.get('/users').then((res) => {
            const data = Array.isArray(res.data) ? res.data : [];
            setUsers(data);
        });
    }, []);

    useEffect(() => {
        if (id) {
            axios.get(`/tasks/${id}`).then((res) => setForm(res.data));
        }
    }, [id]);

    const handleChange = (e) => {
        setForm({ ...form, [e.target.name]: e.target.value });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        const { title, description, deadline, createdByUserId } = form;

        if (!title || !description || !deadline || !createdByUserId) {
            setError('Please fill in all required fields.');
            return;
        }

        try {
            id
                ? await axios.put(`/tasks/${id}`, form)
                : await axios.post('/tasks', form).then((res) => {
                    const task = res.data;
                });

            navigate('/dashboard');
        } catch (err) {
            setError(err.response?.data?.message || 'Submission failed');
        }
    };

    return (
        <div className="min-h-screen bg-white dark:bg-gray-900 text-gray-900 dark:text-white py-10 px-4">
            <div className="max-w-xl mx-auto bg-white dark:bg-gray-800 p-6 rounded-lg shadow">
                <h2 className="text-2xl font-bold mb-6">{id ? 'Edit Task' : 'Create Task'}</h2>
                {error && <p className="text-red-600 mb-4">{error}</p>}

                <form onSubmit={handleSubmit} className="space-y-4">
                    <input
                        name="title"
                        value={form.title}
                        onChange={handleChange}
                        placeholder="Title"
                        className="w-full px-4 py-2 border border-gray-300 dark:border-gray-600 rounded bg-gray-50 dark:bg-gray-700"
                    />

                    <textarea
                        name="description"
                        value={form.description}
                        onChange={handleChange}
                        placeholder="Description"
                        className="w-full px-4 py-2 border border-gray-300 dark:border-gray-600 rounded bg-gray-50 dark:bg-gray-700"
                    />

                    <input
                        type="date"
                        name="deadline"
                        value={form.deadline}
                        onChange={handleChange}
                        className="w-full px-4 py-2 border border-gray-300 dark:border-gray-600 rounded bg-gray-50 dark:bg-gray-700"
                    />

                    <div className="grid grid-cols-2 gap-4">
                        <select
                            name="priority"
                            value={form.priority}
                            onChange={handleChange}
                            className="px-4 py-2 border border-gray-300 dark:border-gray-600 rounded bg-gray-50 dark:bg-gray-700"
                        >
                            <option value="low">Low</option>
                            <option value="medium">Medium</option>
                            <option value="high">High</option>
                        </select>

                        <select
                            name="taskStats"
                            value={form.taskStats}
                            onChange={handleChange}
                            className="px-4 py-2 border border-gray-300 dark:border-gray-600 rounded bg-gray-50 dark:bg-gray-700"
                        >
                            <option value="todo">To Do</option>
                            <option value="in-progress">In Progress</option>
                            <option value="done">Done</option>
                        </select>
                    </div>

                    <select
                        name="createdByUserId"
                        value={form.createdByUserId}
                        onChange={handleChange}
                        className="w-full px-4 py-2 border border-gray-300 dark:border-gray-600 rounded bg-gray-50 dark:bg-gray-700"
                    >
                        <option value="">Select Creator</option>
                        {users.map((user) => (
                            <option key={user.id} value={user.id}>
                                {user.firstName} {user.lastName} ({user.email})
                            </option>
                        ))}
                    </select>

                    <div className="flex justify-end">
                        <button
                            type="submit"
                            className="bg-blue-600 hover:bg-blue-700 text-white px-6 py-2 rounded shadow"
                        >
                            {id ? 'Update' : 'Create'}
                        </button>
                    </div>
                </form>
            </div>
        </div>
    );
};

export default TaskForm;
