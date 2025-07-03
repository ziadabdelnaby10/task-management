import { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { fetchTasks } from '../store/taskSlice';
import { useNavigate } from 'react-router-dom';

const Dashboard = () => {
    const dispatch = useDispatch();
    const navigate = useNavigate();
    const { items = [], totalPages = 1, loading = false } = useSelector((state) => state.tasks || {});
    const [filters, setFilters] = useState({
        search: '',
        status: '',
        priority: '',
        page: 0,
    });

    useEffect(() => {
        dispatch(fetchTasks(filters));
    }, [filters]);

    const handleChange = (e) => {
        setFilters({ ...filters, [e.target.name]: e.target.value, page: 0 });
    };

    const changePage = (delta) => {
        setFilters({ ...filters, page: filters.page + delta });
    };

    return (
        <div className="min-h-screen bg-white dark:bg-gray-900 text-gray-900 dark:text-white transition-colors">
            <div className="max-w-7xl mx-auto px-4 py-6">
                <div className="flex items-center justify-between mb-6">
                    <h1 className="text-3xl font-bold">Task Dashboard</h1>
                    <div className="flex gap-4 items-center">
                        <button
                            onClick={() => navigate('/task/create')}
                            className="bg-blue-600 hover:bg-blue-700 text-white px-4 py-2 rounded transition shadow"
                        >
                            + New Task
                        </button>
                    </div>
                </div>

                {/* Filters */}
                <div className="grid md:grid-cols-4 gap-4 mb-6">
                    <input
                        name="search"
                        value={filters.search}
                        onChange={handleChange}
                        placeholder="Search..."
                        className="p-3 rounded border border-gray-300 dark:border-gray-700 bg-white dark:bg-gray-800"
                    />
                    <select name="status" value={filters.status} onChange={handleChange} className="p-3 rounded border bg-white dark:bg-gray-800">
                        <option value="">All Status</option>
                        <option value="todo">To Do</option>
                        <option value="in-progress">In Progress</option>
                        <option value="done">Done</option>
                    </select>
                    <select name="priority" value={filters.priority} onChange={handleChange} className="p-3 rounded border bg-white dark:bg-gray-800">
                        <option value="">All Priority</option>
                        <option value="low">Low</option>
                        <option value="medium">Medium</option>
                        <option value="high">High</option>
                    </select>
                </div>

                {/* Task List */}
                {loading ? (
                    <div className="text-center text-gray-500">Loading...</div>
                ) : items.length === 0 ? (
                    <div className="text-center text-gray-400">No tasks found.</div>
                ) : (
                    <div className="grid md:grid-cols-3 gap-6">
                        {items.map((task) => (
                            <div
                                key={task.id}
                                onClick={() => navigate(`/task/${task.id}`)}
                                className="cursor-pointer p-4 border rounded-lg bg-white dark:bg-gray-800 hover:shadow-lg transition"
                            >
                                <h2 className="text-lg font-semibold">{task.title}</h2>
                                <p className="text-gray-500 dark:text-gray-400 text-sm mt-1">{task.description}</p>
                                <div className="flex justify-between text-xs text-gray-400 mt-2">
                                    <span>{task.priority}</span>
                                    <span>{task.status}</span>
                                    <span>{new Date(task.dueDate).toLocaleDateString()}</span>
                                </div>
                            </div>
                        ))}
                    </div>
                )}

                {/* Pagination */}
                <div className="flex justify-center mt-10 gap-4">
                    <button
                        onClick={() => changePage(-1)}
                        disabled={filters.page === 1}
                        className="px-4 py-2 bg-gray-300 dark:bg-gray-700 text-gray-800 dark:text-white rounded disabled:opacity-50"
                    >
                        Previous
                    </button>
                    <button
                        onClick={() => changePage(1)}
                        disabled={filters.page === totalPages}
                        className="px-4 py-2 bg-gray-300 dark:bg-gray-700 text-gray-800 dark:text-white rounded disabled:opacity-50"
                    >
                        Next
                    </button>
                </div>
            </div>
        </div>
    );
};

export default Dashboard;
