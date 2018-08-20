import React, {Component} from 'react';
import {connect, bindActionCreators} from 'utils';
import {pull, find, pick, isEqual, forEach, isUndefined, isEmpty, sortBy} from 'lodash';
import {stringify} from 'qs';
import {setReportPageStart, setReportPageSize} from "redux/actions/ReportAction";

import {Table, Action, Button} from 'bqs';

const ActionGroup = Action.Group;
import style from './style.less';

const TableColumn = Table.Column;

//数据源类型排序映射关系
const webDataTypeReflect = {
    // 'cmcc': 1,
    // 'ctcc': 1,
    // 'unicom': 1,
    'tb': 2,
    'jd': 3
};

//序号与结果映射关系
const numberTypeNameReflect = {
    // 1: '运营商',
    2: '淘宝',
    3: '京东'
};

//序号与页面uri的映射关系
const numberTypeReflect = {
    // 1: 'mnoreport',
    2: 'tbreport',
    3: 'jdreport'
};

//@Connect(props, action)
//@Connect(()=>({}), dispatch =>bindActionCreators({}, dispatch))
@connect(({reportReducer, http}, props) => ({
    loading: http.queues.some(queue => ['getReportList', 'searchReportList'].indexOf(queue) > -1),
    ...reportReducer,
    ...props
}), dispatch => bindActionCreators({
    setReportPageStart,
    setReportPageSize
}, dispatch))

export default class extends Component {
    constructor(props) {
        super(props);
        this.state = {};
    }

    componentWillMount() {
    }

    componentDidMount() {
    }

    componentWillReceiveProps(nextProps) {
    }

    //页码改变的回调，参数是改变后的页码及每页条数(点击下一页)Function(page, pageSize)
    onPaginationChange = (page, pageSize) => {
        let {setReportPageStart, setReportPageSize, getReportList, searchParams} = this.props;

        //1.设置pageStart（点击下一页后的当前页 = （当前页码 -1）* 每页条数）
        let pageStart = (page - 1) * pageSize;
        setReportPageStart(pageStart);

        //2. 设置每页条数
        setReportPageSize(pageSize);

        //3. 加载数据，重新渲染表格
        getReportList({start: pageStart, length: pageSize}, searchParams);
    };

    //pageSize 变化的回调 Function(current, size)
    onShowSizeChange = (current, size) => {
        let {setReportPageStart, setReportPageSize, getReportList, searchParams} = this.props;

        //1.设置pageStart（点击下一页后的当前页 = （当前页码 -1）* 每页条数）
        let pageStart = (current - 1) * size;
        setReportPageStart(pageStart);

        //2. 设置每页条数
        setReportPageSize(size);

        //3. 加载数据，重新渲染表格
        getReportList({start: pageStart, length: size}, searchParams);
    };

    openReportPage = (record, type, e) => {
        e.stopPropagation();
        window.open(`/ccp/page/${type}?${stringify(pick(record, ["certNo", "name", "mobile"]))}`)
    };

    getReportAction = (record, type, typeName) => <Button type="primary" key={type}
                                                          onClick={this.openReportPage.bind(null, record, type)}
                                                          size="small">{typeName}</Button>;

    render() {
        let {total, reportList, loading, pageStart, pageSize, dataType} = this.props;
        let {getReportAction} = this;
        return (
            <Table bordered striped /*hover*/
                   loading={loading}
                   rowKey={(report, index) => report.reqId}
                   dataSource={reportList}
                   pagination={{
                       //设置当前页
                       current: parseInt(pageStart / pageSize + 1),
                       total: total,
                       defaultPageSize: pageSize,
                       pageSize: pageSize,
                       onChange: this.onPaginationChange,
                       // showSizeChanger: true,
                       // pageSizeOptions: ['10', '20'],
                       onShowSizeChange: this.onShowSizeChange
                   }}
            >
                <TableColumn
                    title="姓名"
                    dataIndex="name"
                    key="name"
                />
                <TableColumn
                    title="手机号"
                    dataIndex="mobile"
                    key="mobile"
                />
                <TableColumn
                    title="身份证"
                    dataIndex="certNo"
                    key="certNo"
                />
                <TableColumn
                    title="查询时间"
                    dataIndex="storeTime"
                    key="storeTime"
                />
                {
                    isEqual(dataType, "report") && (
                        <TableColumn
                            title="操作"
                            key="action"
                            width={'25%'}
                            dataIndex="reqId"
                            className={style.action}
                            //当前行的值, 当前行数据, 索引
                            render={(text, record, index) => {
                                // this.getReportAction(text, record, index);
                                //获取出用户授权的所有数据源，并进行数值映射
                                let webDataTypes = record.webDataTypes;
                                let typeNums = [];
                                forEach(webDataTypes, function (value) {
                                    let tempNum = webDataTypeReflect[value];
                                    (!isUndefined(tempNum)) && typeNums.push(tempNum);
                                });

                                //根据数据进行排序，拼接html
                                let actionHtml = [];
                                actionHtml.push(this.getReportAction(record, dataType, "资信云"));
                                if (!isEmpty(typeNums)) {
                                    typeNums = sortBy(typeNums, function (typeNum) {
                                        return typeNum;
                                    });
                                    forEach(typeNums, function (number) {
                                        //类型（用于拼接请求uri）
                                        let tempType = numberTypeReflect[number];
                                        //类型名称（用于显示）
                                        let tempTypeName = numberTypeNameReflect[number];
                                        actionHtml.push(getReportAction(record, tempType, tempTypeName));
                                    });
                                }
                                return <ActionGroup>{actionHtml}</ActionGroup>;
                            }}
                        />
                    )
                }
                {
                    isEqual(dataType, "reportouter") && (
                        <TableColumn
                            title="操作"
                            key="action"
                            dataIndex="reqId"
                            //当前行的值, 当前行数据, 索引
                            render={(text, record, index) => (
                                <ActionGroup>
                                    <Action key="openReport" className={style.oper} icon="zoom-times"
                                            onClick={this.openReportPage.bind(null, record, dataType)}/>
                                </ActionGroup>
                            )}
                        />
                    )
                }

            </Table>
        )
    }
}
