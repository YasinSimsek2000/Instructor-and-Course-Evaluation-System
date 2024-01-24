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
} from 'mdb-react-ui-kit';

export const ForgotPassword = () => {
    const [email, setEmail] = useState<string | undefined>();
    const navigate = useNavigate();

    const [jwtToken, setJwtToken] = useState<string | undefined>();

    const [justifyActive, setJustifyActive] = useState('tab1');

    function ForgotPasswordRequest() {
        const token = localStorage.getItem('jwtToken')
        axios.defaults.headers['Authorization'] = token;
        axios.post("http://localhost:8081/reset-password/save", {
            email
        })
            . then(response => {
                if (response.status == 200) {
                    alert("The request is sent successfully!")

                } else {
                    alert(response.data)
                }
            })
    }

    return (
        <div className={"log-in"}>
            <MDBContainer className="p-3 my-5 d-flex flex-column w-50 log-in-1">

                <MDBTabs pills justify className='mb-3 d-flex flex-row justify-content-between'>
                    <MDBTabsItem>
                        <MDBTabsLink>
                            You will get an e-mail with your new password. Please change it after log in.
                        </MDBTabsLink>
                    </MDBTabsItem>
                </MDBTabs>

                <MDBTabsContent>

                    <MDBTabsPane show={justifyActive === 'tab1'}>

                        <MDBInput wrapperClass='mb-4' label='Email address' id='email1' type='email'
                                  onChange={event => setEmail(event.target.value)}/>

                        <MDBBtn className="mb-4 w-100" onClick={() => ForgotPasswordRequest()}>Send Password Reset
                            Request</MDBBtn>

                    </MDBTabsPane>
                </MDBTabsContent>
            </MDBContainer>
        </div>
    )
}
