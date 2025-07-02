import { createSlice, createAsyncThunk } from '@reduxjs/toolkit';
import axios from '../utils/axiosInstance';

export const fetchTasks = createAsyncThunk('tasks/fetch', async (params) => {
    const res = await axios.get('/tasks', { params });
    return {
        tasks: res.data.content,
        totalPages: res.data.page.totalPages,
    };
});

const taskSlice = createSlice({
    name: 'tasks',
    initialState: {
        items: [],
        totalPages: 1,
        loading: false,
    },
    reducers: {},
    extraReducers: (builder) => {
        builder
            .addCase(fetchTasks.pending, (state) => {
                state.loading = true;
            })
            .addCase(fetchTasks.fulfilled, (state, action) => {
                state.items = action.payload.tasks;
                state.totalPages = action.payload.totalPages;
                state.loading = false;
            })
            .addCase(fetchTasks.rejected, (state) => {
                state.loading = false;
            });
    },
});

export default taskSlice.reducer;
