import React, {useState} from "react";
import {Navigation} from "../components/Navigation";
import {Col, Row} from "antd";
import {MDBBtn, MDBCard, MDBCardBody, MDBCardFooter, MDBCardHeader, MDBInput, MDBTypography} from "mdb-react-ui-kit";
import {LeftSideManage} from "../components/LeftSideManage";
import "../scss/ManageAccount.scss";
import axios from "axios";
import {useNavigate} from "react-router-dom";

export const ManageAccount = () => {
    const [email1, setEmail1] = useState("");
    const [email2, setEmail2] = useState("");

    const [oldPassword, setOldPassword] = useState("");
    const [newPassword, setNewPassword] = useState("");

    const navigate = useNavigate();

    const handleSaveChanges = () => {
        const token = localStorage.getItem('jwtToken')
        axios.defaults.headers['Authorization'] = token;
        axios.put("http://localhost:8081/user/self-update", {
            email: email1,
            secondEmail: email2,
        })
            . then(response => {
                if (response.status === 200) {
                    alert("Successful");
                }
            })
            .catch(error => {

            });
    };

    const handleUpdatePassword = () => {
        const token = localStorage.getItem('jwtToken')
        axios.defaults.headers['Authorization'] = token;
        axios.put("http://localhost:8081/user/self-reset-password", {
            oldPassword: oldPassword,
            newPassword: newPassword,
        })
            . then(response => {
                if (response.status === 200) {
                    navigate('/')
                }
            })
            .catch(error => {

            });
    };

    return (
        <div>
            <Navigation />
            <Row>
                <Col className="col-3">
                    <LeftSideManage />
                </Col>
                <Col className="col-9">
                    <MDBCard className="m-2">
                        <MDBCardHeader className="m-0 card-title">
                            <MDBTypography className="card-title" variant="h1">Manage Account</MDBTypography>
                        </MDBCardHeader>
                        <MDBCardBody className="m-1 p-1">
                            <div className="input-container">
                                <label>1st Email</label>
                                <MDBInput
                                    type="email"
                                    wrapperClass="input"
                                    size = "sm"
                                    value={email1}
                                    onChange={(e) => setEmail1(e.target.value)}
                                />
                            </div>
                            <div className="input-container">
                                <label>2nd Email</label>
                                <MDBInput
                                    type="email"
                                    wrapperClass="input"
                                    size = "sm"
                                    value={email2}
                                    onChange={(e) => setEmail2(e.target.value)}
                                />
                            </div>
                        </MDBCardBody>
                        <MDBCardFooter className="d-flex justify-content-end">
                            <MDBBtn className="m-1 w-25" color="success"  onClick={handleSaveChanges}>
                                Save Changes
                            </MDBBtn>
                        </MDBCardFooter>
                    </MDBCard>

                    <MDBCard className="m-2">
                        <MDBCardHeader className="m-0 card-title">
                            <MDBTypography className="card-title" variant="h1">Change Password</MDBTypography>
                        </MDBCardHeader>
                        <MDBCardBody className="m-1 p-1">
                            <div className="input-container">
                                <label>Old Password</label>
                                <MDBInput
                                    type="password"
                                    wrapperClass="input"
                                    value={oldPassword}
                                    onChange={(e) => setOldPassword(e.target.value)}
                                />
                            </div>
                            <div className="input-container">
                                <label>New Password</label>
                                <MDBInput
                                    type="password"
                                    wrapperClass="input"
                                    value={newPassword}
                                    onChange={(e) => setNewPassword(e.target.value)}
                                />
                            </div>
                        </MDBCardBody>
                        <MDBCardFooter className="d-flex justify-content-end">
                            <MDBBtn className="m-1" color="success" onClick={handleUpdatePassword}>
                                Update Password
                            </MDBBtn>
                        </MDBCardFooter>
                    </MDBCard>
                </Col>
            </Row>
        </div>
    );
};
