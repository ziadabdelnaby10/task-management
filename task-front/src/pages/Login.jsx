import { useState } from 'react';
import { useDispatch } from 'react-redux';
import axios from '../utils/axiosInstance';
import { loginSuccess } from '../store/authSlice';
import { useNavigate } from 'react-router-dom';

const Login = () => {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const dispatch = useDispatch();
    const navigate = useNavigate();

    const handleSubmit = async (e) => {
        e.preventDefault();
        const res = await axios.post('/auth/login', { email, password });
        dispatch(loginSuccess(res.data.token));
        navigate('/dashboard');
    };

    return (
        <div className="min-h-screen flex items-center justify-center bg-gray-50 dark:bg-gray-900 px-4">
            <div className="max-w-md w-full bg-white dark:bg-gray-800 p-8 rounded shadow-lg">
                <h2 className="text-2xl font-bold text-center text-gray-800 dark:text-white mb-6">Login to your
                    account</h2>
                <form onSubmit={handleSubmit} className="space-y-5">
                    <div>
                        <label className="block text-sm text-gray-600 dark:text-gray-300">Email</label>
                        <input
                            type="email"
                            required
                            className="w-full mt-1 px-4 py-2 border border-gray-300 dark:border-gray-600 rounded bg-gray-50 dark:bg-gray-700 text-gray-800 dark:text-white"
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                            placeholder="you@example.com"
                        />
                    </div>
                    <div>
                        <label className="block text-sm text-gray-600 dark:text-gray-300">Password</label>
                        <input
                            type="password"
                            required
                            className="w-full mt-1 px-4 py-2 border border-gray-300 dark:border-gray-600 rounded bg-gray-50 dark:bg-gray-700 text-gray-800 dark:text-white"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            placeholder="••••••••"
                        />
                    </div>
                    <button
                        type="submit"
                        className="w-full bg-blue-600 hover:bg-blue-700 text-white py-2 rounded transition"
                    >
                        Login
                    </button>
                </form>
                <p className="text-center text-sm text-gray-500 dark:text-gray-400 mt-4">
                    Don't have an account?{' '}
                    <span onClick={() => navigate('/signup')} className="text-blue-600 hover:underline cursor-pointer">
            Sign up
          </span>
                </p>
            </div>
        </div>
    );
};

export default Login;
