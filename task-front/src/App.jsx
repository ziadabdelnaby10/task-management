import {BrowserRouter, Routes, Route, Navigate} from 'react-router-dom';
import Dashboard from './pages/Dashboard';
import TaskForm from './pages/TaskForm';
import TaskDetails from './pages/TaskDetails';
import Login from './pages/Login';
import Signup from './pages/Signup';
import {useSelector} from 'react-redux';
import DarkModeToggle from "./components/DarkModeToggle";

const App = () => {
    const isAuth = useSelector((state) => state.auth.isAuthenticated);

    return (
        <>
            <div
                className="min-h-screen bg-white text-gray-900 dark:bg-gray-900 dark:text-white transition-colors duration-300">
                <div className="p-4 flex justify-end">
                    <DarkModeToggle/>
                </div>
                <BrowserRouter>
                    <Routes>
                        <Route path="/login" element={<Login/>}/>
                        <Route path="/signup" element={<Signup/>}/>
                        <Route path="/dashboard" element={isAuth ? <Dashboard/> : <Navigate to="/login"/>}/>
                        <Route path="/task/create" element={<TaskForm/>}/>
                        <Route path="/task/edit/:id" element={<TaskForm/>}/>
                        <Route path="/task/:id" element={<TaskDetails/>}/>
                        <Route path="*" element={<Navigate to="/dashboard"/>}/>
                    </Routes>
                </BrowserRouter>
            </div>
        </>
    );
};

export default App;
