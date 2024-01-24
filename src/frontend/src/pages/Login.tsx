import React, {useState} from 'react';
import axios from 'axios';
import '../scss/Login.scss'
import {useNavigate} from "react-router-dom";
import {
    MDBBtn,
    MDBContainer,
    MDBInput,
    MDBTabs,
    MDBTabsContent,
    MDBTabsItem,
    MDBTabsLink,
    MDBTabsPane,
    MDBTextArea
} from 'mdb-react-ui-kit';

export const Login = () => {
    const [username, setUsername] = useState<string | undefined>();
    const [password, setPassword] = useState<string | undefined>();
    const [name, setName] = useState<string | undefined>();
    const [surname, setSurname] = useState<string | undefined>();
    const [detail, setDetail] = useState<string | undefined>();
    const [email, setEmail] = useState<string | undefined>();
    const [message, setMessage] = useState<string | "Can you register me?">();
    const [role, setRole] = useState<string | "STUDENT">();
    const navigate = useNavigate();


    const [justifyActive, setJustifyActive] = useState('tab1');

    const handleJustifyClick = (value: React.SetStateAction<string>) => {
        if (value === justifyActive) {
            return;
        }
        setJustifyActive(value);
    };

    function LogIn() {
        if (!username) {
            alert("Username cannot be empty!");
            return;
        }
        if (!password) {
            alert("Password cannot be empty!");
            return;
        }

        axios.post("http://localhost:8081/connection/authenticate", {username, password})
            . then(response => {
                const data = {
                    accessToken: response.data.access_token,
                    refreshToken: response.data.refresh_token,
                }

                if (response.data.access_token) {
                    const token = "Bearer ".concat(response.data.access_token);
                    localStorage.setItem('jwtToken', token)
                    axios.defaults.headers['Authorization'] = token
                    navigate('/home', {state: {data}})
                }
            }).catch(error => alert("Wrong credentials!"))
    }

    function Enroll() {
        if (!name) {
            alert("Name cannot be empty!");
            return;
        }
        if (!surname) {
            alert("Surname cannot be empty!");
            return;
        }
        if (!email) {
            alert("Email cannot be empty!");
            return;
        }
        if (!detail) {
            alert("Student number cannot be empty!");
            return;
        }

        const fullName = name?.concat(surname ? " " + surname : "")
        setMessage(message ? message : "Can you register me?");
        setRole('STUDENT')
        axios.post("http://localhost:8081/connection/enroll", {
            email,
            fullName,
            detail,
            message,
            role
        })
            . then(response => {

                if (response.status == 200) {

                } else {
                    alert(response.data);
                }
            })
    }

    return (
        <div className={"log-in"}>
            <MDBContainer className="p-3 my-5 d-flex flex-column w-50 log-in-1">

                <MDBTabs pills justify className='mb-3 d-flex flex-row justify-content-between'>
                    <MDBTabsItem>
                        <MDBTabsLink onClick={() => handleJustifyClick('tab1')} active={justifyActive === 'tab1'}>
                            Login
                        </MDBTabsLink>
                    </MDBTabsItem>
                    <MDBTabsItem>
                        <MDBTabsLink onClick={() => handleJustifyClick('tab2')} active={justifyActive === 'tab2'}>
                            Enroll
                        </MDBTabsLink>
                    </MDBTabsItem>
                </MDBTabs>

                <MDBTabsContent>

                    <MDBTabsPane show={justifyActive === 'tab1'}>

                        <MDBInput wrapperClass='mb-4' label='Username' id='email2' type='email'
                                  onChange={event => setUsername(event.target.value)}/>
                        <MDBInput wrapperClass='mb-4' label='Password' id='form2' type='password'
                                  onChange={event => setPassword(event.target.value)}/>

                        <div className="d-flex justify-content-between mx-4 mb-4">
                            <a href="forgot-password">Forgot password?</a>
                        </div>

                        <MDBBtn className="mb-4 w-100" onClick={() => LogIn()}>Log In</MDBBtn>

                    </MDBTabsPane>

                    <MDBTabsPane show={justifyActive === 'tab2'}>

                        <MDBInput wrapperClass='mb-4' label='Name' type='text'
                                  onChange={event => setName(event.target.value)}/>
                        <MDBInput wrapperClass='mb-4' label='Surname' type='text'
                                  onChange={event => setSurname(event.target.value)}/>
                        <MDBInput wrapperClass='mb-4' label='Student Number' type='text'
                                  onChange={event => setDetail(event.target.value)}/>
                        <MDBInput wrapperClass='mb-4' label='Email' type='email'
                                  onChange={event => setEmail(event.target.value)}/>
                        <MDBTextArea wrapperClass='mb-4' label='Message'
                                     placeholder='Can you register me?'
                                     onChange={event => setMessage(event.target.value)}/>

                        <MDBBtn className="mb-4 w-100" onClick={() => Enroll()}>Send Enrollment Request</MDBBtn>

                    </MDBTabsPane>

                </MDBTabsContent>

            </MDBContainer>
        </div>
    )
}
