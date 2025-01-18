
import {useEffect, useState} from 'react'
import axios from 'axios'
import Modal from 'react-bootstrap/Modal'
import {Card, Col, Row, Image} from 'react-bootstrap'

export default function AllSubmissions() {
    const [submissions, setSubmissions] = useState([])

    useEffect(() => {
        getData()
    }, [])

    const getData = async () => {
        const response = await axios.get("http://localhost:8080/api/submissions")
            .then((response) => {
                setSubmissions(response.data)
            }).catch(() => {
              alert("Unknown server error")
            })
        console.log(response)
    }

    const [showModal, setShowModal] = useState(false);
    const [selectedSubmission, setSelectedSubmission] = useState(null);

    const handleCardClick = (submission) => {
        setSelectedSubmission(submission);
        setShowModal(true);
    };

    const wrapUrl = (url) => {
        return `http://localhost:8080/images/generated/${url}`
    }

    return (
        <div style={{paddingBlock: "30px"}}>
            <Row xs={1} md={2} lg={3} className="g-4">
                {submissions.map((submission) => (
                    <Col key={submission.id}>
                        <Card
                            onClick={() => handleCardClick(submission)}
                            className="h-100 cursor-pointer"
                            style={{ cursor: 'pointer' }}
                        >
                            <Card.Img
                                variant="top"
                                src={wrapUrl(submission.url)}
                                style={{ height: '200px', objectFit: 'cover' }}
                            />
                            <Card.Body>
                                <Card.Title>{submission.title}</Card.Title>
                                <Card.Text>
                                    <strong>Name:</strong> {submission.name}<br/>
                                    <strong>Country:</strong> {submission.country}
                                </Card.Text>
                            </Card.Body>
                        </Card>
                    </Col>
                ))}
            </Row>

            <Modal
                show={showModal}
                onHide={() => setShowModal(false)}
                size="lg"
                centered
            >
                {selectedSubmission && (
                    <>
                        <Modal.Header closeButton>
                            <Modal.Title>{selectedSubmission.name}'s Submission</Modal.Title>
                        </Modal.Header>
                        <Modal.Body>
                            <Image
                                src={wrapUrl(selectedSubmission.url)}
                                fluid
                                className="mb-3"
                            />
                            <h4>Details:</h4>
                            <p><strong>Name:</strong> {selectedSubmission.name}</p>
                            <p><strong>Title:</strong> {selectedSubmission.title}</p>
                            <p><strong>Country:</strong> {selectedSubmission.country}</p>
                        </Modal.Body>
                    </>
                )}
            </Modal>
        </div>
    )
}