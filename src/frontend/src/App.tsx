import React from 'react';
import './App.css';
import {BrowserRouter, Route, Routes} from "react-router-dom";
import {Login} from "./pages/Login";
import {ForgotPassword} from "./pages/ForgotPassword";
import {Home} from "./pages/Home";
import {DepartmentManagers} from "./pages/DepartmentManagers";
import {Instructors} from "./pages/Instructors";
import {Students} from "./pages/Students";
import {Courses} from "./pages/Courses";
import {ManageAccount} from "./pages/ManageAccount";

function App() {
  return (
    <div className="App">
            <BrowserRouter>
                <Routes>
                    <Route path={""} element={<Login></Login>}></Route>
                    <Route path={"forgot-password"} element={<ForgotPassword></ForgotPassword>}></Route>
                    <Route path={"home"} element={<Home></Home>}></Route>
                    <Route path={"department-managers"} element={<DepartmentManagers></DepartmentManagers>}></Route>
                    <Route path={"instructors"} element={<Instructors></Instructors>}></Route>
                    <Route path={"students"} element={<Students></Students>}></Route>
                    <Route path={"courses"} element={<Courses></Courses>}></Route>
                    <Route path ={"manage"} element={<ManageAccount></ManageAccount>}></Route>
                </Routes>
            </BrowserRouter>
    </div>
  );
}

export default App;
