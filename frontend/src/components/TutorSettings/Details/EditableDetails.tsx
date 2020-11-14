import React, {useEffect} from "react";
import {
  Button,
  Card,
  Col,
  DatePicker,
  Form,
  Input,
  Radio,
  Row,
  Select,
  message,
} from "antd";
import { qualifications, races } from "../../../config/constants";
import moment from 'moment';
import {TutorService} from "../../../services/Tutor";
import ProfileAvatar from "./Upload";
const countryJson = require("../../../config/countries.json");

const { Option } = Select;

const layout = {
  labelCol: { span: 10 },
  wrapperCol: { span: 12 },
};
const tailLayout = {
  wrapperCol: { offset: 20, span: 4 },
};

type editableDetailsProps = {
  user: tutorDataType;
}

const EditableDetails = (props: editableDetailsProps) => {
  const [form] = Form.useForm();
  const { user } = props;

  useEffect(() => {
    form.setFieldsValue({
        ...user,
      dob: moment(user.dob.split("[")[0])

    });
  },[user]);

  const updateUserDetails = async (userId: number, data: tutorDataType): Promise<void> => {
    const response = await TutorService.updateTutorDetails(data);
    if(response) {
      message.success("Successfully updated your details")
    } else {
      message.error("Unable to save user details");
    }
  }
  const onFinish = (fieldsValue: any) => {
    const values = {
      ...fieldsValue,
      dob: fieldsValue["dob"].format("DD-MM-YYYY"),
    };

    const newVal = {
        ...user,
        ...values
    }
    updateUserDetails(user.personId, newVal);

  };

  const radioStyle = {
    display: "block",
    height: "30px",
    lineHeight: "30px",
  };

  return (
    <Card>
      <Row style={{ marginBottom: "30px", fontWeight: 600, fontSize: "18px" }}>
        Personal Details
      </Row>
      <Form {...layout} form={form} name="control-hooks" onFinish={onFinish}>
        <Row>
          <Col span={12}>
            <Form.Item
              name="firstName"
              label="First Name"
              rules={[{ required: true }]}
            >
              <Input />
            </Form.Item>
            <Form.Item
              name="lastName"
              label="Last Name"
              rules={[{ required: true }]}
            >
              <Input />
            </Form.Item>

            <Form.Item name="race" label="Race" rules={[{ required: true }]}>
              <Select placeholder="Select your race" allowClear>
                {races.map((r: string, i: number) => (
                  <Option value={r} key={i}>
                    {" "}
                    {r}
                  </Option>
                ))}
              </Select>
            </Form.Item>

            <Form.Item
              name="mobileNum"
              label="Mobile No."
              rules={[{ required: true }]}
            >
              <Input />
            </Form.Item>

            <Form.Item
                name="citizenship"
                label="Citizenship"
                rules={[{ required: true }]}
            >
              <Select showSearch placeholder="Select a country" allowClear>
                {countryJson.map(
                    (c: { name: string; code: string }, i: number) => (
                        <Option value={c.name.replace(/ /g,"_") } key={i}>
                          {" "}
                          {c.name}
                        </Option>
                    )
                )}
              </Select>
            </Form.Item>
          </Col>

          <Col span={12}>

            <Form.Item
                name="profileImg"
                label="Profile Image"
            >
              <ProfileAvatar imgUrl={user.profileImage}/>
            </Form.Item>

            <Form.Item
              name="dob"
              label="Date of birth"
              rules={[
                {
                  type: "object",
                  required: true,
                  message: "Please select time!",
                },
              ]}
            >
              <DatePicker format="DD-MM-YYYY" />
            </Form.Item>

            <Form.Item
              name="gender"
              label="Gender"
              rules={[{ required: true }]}
            >
              <Radio.Group>
                <Radio style={radioStyle} value="MALE">
                  Male
                </Radio>
                <Radio style={radioStyle} value="FEMALE">
                  Female
                </Radio>
                <Radio style={radioStyle} value="OTHER">
                  Other
                </Radio>
              </Radio.Group>
            </Form.Item>

            <Form.Item
              name="highestQualification"
              label="Highest Qualfication"
              rules={[{ required: true }]}
            >
              <Select placeholder="Bachelor" allowClear>
                {qualifications.map((q: string, i: number) => (
                  <Option value={q} key={i}>
                    {" "}
                    {q}
                  </Option>
                ))}
              </Select>
            </Form.Item>
          </Col>
        </Row>

        <Form.Item
          name="profileDesc"
          label="Description"
          rules={[{ required: true }]}
          wrapperCol={{ span: 18 }}
          labelCol={{ span: 5 }}
        >
          <Input.TextArea rows={5} />
        </Form.Item>

        <Form.Item {...tailLayout}>
          <Button type="primary" htmlType="submit">
            Submit
          </Button>
        </Form.Item>
      </Form>
    </Card>
  );
};

export default EditableDetails;
