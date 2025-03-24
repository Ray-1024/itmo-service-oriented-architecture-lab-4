const utils = {
    asString: function (id) {
        let element = document.getElementById(id);
        if (element != null) return element.value.toString();
        else {
            element = document.querySelector(`input[name="${id}"]:checked`).value;
            return element;
        }
    },
    toInt: function (value) {
        if (value == null) return null;
        let val = parseInt(value.toString())
        return (isFinite(val) && val.toString() === value.toString()) ? val : null;
    },
    asInt: function (id) {
        return this.toInt(this.asString(id))
    },
    toFloat: function (value) {
        if (value == null) return null;
        let val = parseFloat(value.toString().replace(",", "."));
        return (isFinite(val) && val.toString() === value.toString().replace(",", ".")) ? val : null;
    },
    asFloat: function (id) {
        return this.toFloat(this.asString(id))
    },
    isVisible: function (id) {
        let element = document.getElementById(id);
        if (element == null) return false;
        return element.style.display === '';
    },
    show: function (id) {
        let element = document.getElementById(id);
        if (element != null) {
            element.style.display = '';
        }
    },
    hide: function (id) {
        let element = document.getElementById(id);
        if (element != null) {
            element.style.display = 'none';
        }
    },
    hideAllFields: function () {
        this.hide('pagingDiv');
        this.hide('hidePagingDiv');
        this.hide('pageNumberDiv');
        this.hide('pageSizeDiv');

        this.hide('sortingDiv');
        this.hide('sortingFieldDiv');
        this.hide('sortingOrderDiv');

        this.hide('filteringDiv');
        this.hide('filteringFieldDiv');
        this.hide('filteringComparatorDiv');
        this.hide('filteringValueDiv');

        this.hide('routeDiv');
        this.hide('routeIdDiv');
        this.hide('routeNameDiv');
        this.hide('routeCoordinatesXDiv');
        this.hide('routeCoordinatesYDiv');
        this.hide('routeCreationDateDiv');
        this.hide('routeFromFramedBlock');
        this.hide('hideFromDiv');
        this.hide('routeFromXDiv');
        this.hide('routeFromYDiv');
        this.hide('routeFromZDiv');
        this.hide('routeFromNameDiv');
        this.hide('routeToXDiv');
        this.hide('routeToYDiv');
        this.hide('routeToZDiv');
        this.hide('routeToNameDiv');
        this.hide('routeDistanceDiv');


        document.getElementById('hidePaging').checked = false;
        document.getElementById('hideFrom').checked = false;
    },
    clearErrors: function () {
        document.getElementById('errorDiv').innerHTML = '';
    },
    header: function (text) {
        document.getElementById('headerDiv').innerText = text;
    },
    error: function (text) {
        document.getElementById('errorDiv').innerHTML +=
            '<div class="framedBlock">' +
            text
            + '</div>';
    },
    resultRouteTable: function (routes) {
        console.log(routes);

        function clippedCell(text, maxLength) {
            return text.length <= maxLength ? `<td>${text}</td>` : `<td>${text.substring(0, maxLength - 3)}...</td>`;
        }

        let txt = '<table>';
        txt += '<tr>';
        txt += '<td>id</td>';
        txt += '<td>name</td>';
        txt += '<td>coordinates.x</td>';
        txt += '<td>coordinates.y</td>';
        txt += '<td>creationDate</td>';
        txt += '<td>from.x</td>';
        txt += '<td>from.y</td>';
        txt += '<td>from.z</td>';
        txt += '<td>from.name</td>';
        txt += '<td>to.x</td>';
        txt += '<td>to.y</td>';
        txt += '<td>to.z</td>';
        txt += '<td>to.name</td>';
        txt += '<td>distance</td>';
        txt += '</tr>';
        if (routes !== undefined && routes !== null) {
            routes.forEach(function (item) {
                txt += '<tr>';
                txt += clippedCell(item.id.toString(), 8);
                txt += clippedCell(item.name.toString(), 8);
                txt += clippedCell(item.coordinates.x.toString(), 8);
                txt += clippedCell(item.coordinates.y.toString(), 8);
                txt += clippedCell(item.creationDate.toString(), 23);
                if (item.from !== null && item.from !== undefined) {
                    if (item.from.x !== undefined)
                        txt += clippedCell(item.from.x.toString(), 8);
                    else txt += '<td></td>';
                    if (item.from.y !== undefined)
                        txt += clippedCell(item.from.y.toString(), 8);
                    else txt += '<td></td>';
                    if (item.from.z !== undefined)
                        txt += clippedCell(item.from.z.toString(), 8);
                    else txt += '<td></td>';
                    if (item.from.name !== undefined)
                        txt += clippedCell(item.from.name.toString(), 8);
                    else txt += '<td></td>';
                } else {
                    txt += '<td></td><td></td><td></td><td></td>';
                }
                txt += clippedCell(item.to.x.toString(), 8);
                txt += clippedCell(item.to.y.toString(), 8);
                txt += clippedCell(item.to.z.toString(), 8);
                txt += clippedCell(item.to.name.toString(), 8);
                txt += clippedCell(item.distance.toString(), 8);
                txt += '</tr>';
            });
        }
        txt += '</table>';
        document.getElementById('resultDiv').innerHTML = txt;
    },
    resultGroupsInfo: function (groupsInfo) {
        function clippedCell(text, maxLength) {
            return text.length <= maxLength ? `<td>${text}</td>` : `<td>${text.substring(0, maxLength - 3)}...</td>`;
        }

        let txt = '<table>';
        txt += '<tr>';
        txt += '<td>name</td>';
        txt += '<td>count</td>';
        txt += '</tr>';
        if (groupsInfo !== undefined && groupsInfo !== null) {
            groupsInfo.forEach(function (item) {
                txt += '<tr>';
                txt += clippedCell(item.name.toString(), 8);
                txt += clippedCell(item.count.toString(), 8);
                txt += '</tr>';
            });
        }
        txt += '</table>';
        document.getElementById('resultDiv').innerHTML = txt;
    },
    resultText: function (text) {
        document.getElementById('resultDiv').innerHTML = text;
    },
    handleErrorResponse: function (errorResponse) {
        let txt = 'Ошибка: ' + errorResponse.message;
        this.error(txt);
    },
    handleInvalidParamsResponse: function (invalidParamsResponse) {
        let txt = 'Один или несколько введенных параметров неверны: ' + invalidParamsResponse.error.message;
        this.error(txt);
    },
    handleNon200Response: function (response, data) {
        try {
            utils.clearErrors();
            const error = this.parseXml(data);
            if (response.status === 200 || response.status === 204) {
            } else if (response.status === 400 || response.status === 422) {
                this.error('Неверно заполнено одно из полей');
                this.resultText('');
                this.handleInvalidParamsResponse(error);
            } else if (response.status === 500) {
                this.error('Внутренняя ошибка сервера');
                this.resultText('');
                this.handleErrorResponse(error);
            } else if (response.status === 404) {
                this.error('Произошла ошибка');
                this.resultText('');
                this.handleErrorResponse(error);
            } else {
                this.error(`Неожиданный код ответа ${response.status}`);
                this.resultText('');
                console.log(response);
                console.log(error);
            }
        } catch (error) {
            console.log(error);
        }
    },
    parseXml: function (xmlString) {
        xmlString.toString().replace('<?xml version="1.0" encoding="UTF-8" standalone="yes"?>', '');
        const parser = new DOMParser();
        const xmlDoc = parser.parseFromString(xmlString, "text/xml");

        function parseNode(node) {
            const obj = {};
            for (let i = 0; i < node.childNodes.length; i++) {
                const child = node.childNodes[i];
                if (child.nodeType === 1) {
                    const nodeName = child.nodeName;
                    const nodeValue = parseNode(child);
                    if (obj[nodeName]) {
                        if (!Array.isArray(obj[nodeName])) {
                            obj[nodeName] = [obj[nodeName]];
                        }
                        obj[nodeName].push(nodeValue);
                    } else {
                        obj[nodeName] = nodeValue;
                    }
                } else if (child.nodeType === 3 && child.nodeValue.trim() !== "") {
                    return child.nodeValue.trim();
                }
            }

            return Object.keys(obj).length === 0 ? "" : obj;
        }

        return parseNode(xmlDoc.documentElement);
    },
    getFields: function () {
        let fields = {};
        if (utils.isVisible('pagingDiv') && utils.isVisible('pageSizeDiv') && utils.isVisible('pageNumberDiv')) {
            fields.pageSize = utils.asInt('pageSize');
            fields.pageNumber = utils.asInt('pageNumber');
        }
        if (utils.isVisible('sortingDiv') && utils.isVisible('sortingFieldDiv') && utils.isVisible('sortingOrderDiv')) {
            //fields.sorting = utils.asString('sortingOrder') + utils.asString('sortingField');
            fields.sorting = document.getElementById('sortingParamsList').innerText;
        }
        if (utils.isVisible('filteringDiv') && utils.isVisible('filteringFieldDiv') && utils.isVisible('filteringComparatorDiv') && utils.isVisible('filteringValueDiv')) {
            /*fields.filtering =
                utils.asString('filteringField') +
                utils.asString('filteringComparator') +
                utils.asString('filteringValue');*/
            fields.filtering = document.getElementById('filteringParamsList').innerText;
        }
        if (utils.isVisible('routeDiv')) {
            if (utils.isVisible('routeIdDiv')) fields.routeId = utils.asInt('routeId');
            if (utils.isVisible('routeNameDiv')) fields.routeName = utils.asString('routeName');
            if (utils.isVisible('routeCoordinatesXDiv')) fields.routeCoordinatesX = utils.asInt('routeCoordinatesX');
            if (utils.isVisible('routeCoordinatesYDiv')) fields.routeCoordinatesY = utils.asInt('routeCoordinatesY');
            if (utils.isVisible('routeCreationDateDiv')) fields.routeCreationDate = `${utils.asString('routeCreationDate')}T00:00:00Z`;
            if (utils.isVisible('routeFromFramedBlock')) {
                if (utils.isVisible('routeFromXDiv')) fields.routeFromX = utils.asInt('routeFromX');
                if (utils.isVisible('routeFromYDiv')) fields.routeFromY = utils.asInt('routeFromY');
                if (utils.isVisible('routeFromZDiv')) fields.routeFromZ = utils.asFloat('routeFromZ');
                if (utils.isVisible('routeFromNameDiv')) fields.routeFromName = utils.asString('routeFromName');
            }
            if (utils.isVisible('routeToXDiv')) fields.routeToX = utils.asInt('routeToX');
            if (utils.isVisible('routeToYDiv')) fields.routeToY = utils.asInt('routeToY');
            if (utils.isVisible('routeToZDiv')) fields.routeToZ = utils.asFloat('routeToZ');
            if (utils.isVisible('routeToNameDiv')) fields.routeToName = utils.asString('routeToName');
            if (utils.isVisible('routeDistanceDiv')) fields.routeDistance = utils.asInt('routeDistance');
        }
        console.log(fields);
        return fields;
    },
};

const modes = {
    getRoutes: {
        activate: function () {
            utils.header('Получение маршрутов');
            utils.show('pagingDiv');
            utils.show('hidePagingDiv');
            utils.show('pageSizeDiv');
            utils.show('pageNumberDiv');
            utils.show('sortingDiv');
            utils.show('hideSortingDiv');
            utils.show('sortingFieldDiv');
            utils.show('sortingOrderDiv');
            utils.show('filteringDiv');
            utils.show('hideFilteringDiv');
            utils.show('filteringFieldDiv');
            utils.show('filteringComparatorDiv');
            utils.show('filteringValueDiv');
        },
        executeRequest: async function () {
            utils.clearErrors();
            utils.resultText('');
            const url = new URL('http://localhost:7000/api/v1/routes');
            const fields = utils.getFields();
            if (fields.pageSize !== undefined ?? fields.pageNumber !== undefined) {
                url.searchParams.append('size', `${fields.pageSize}`);
                url.searchParams.append('page', `${fields.pageNumber}`);
            }
            if (fields.sorting !== undefined) {
                url.searchParams.append('sort', `${fields.sorting}`);
            }
            if (fields.filtering !== undefined) {
                url.searchParams.append('filter', `${fields.filtering}`);
            }
            try {
                const response = await fetch(url, {

                    method: 'GET',
                    headers: {
                        'Content-Type': 'application/xml',
                        'Accept': 'application/xml'
                    }
                });
                const data = await response.text();
                console.log(data);
                if (response.status === 200) {
                    const routesResponse = utils.parseXml(data);
                    console.log(routesResponse);
                    if (Array.isArray(routesResponse.routes.route))
                        utils.resultRouteTable(routesResponse.routes.route);
                    else
                        utils.resultRouteTable([routesResponse.routes.route]);
                }
                utils.handleNon200Response(response, data);
            } catch (error) {
                console.log(error);
            }
        }
    },
    createRoute: {
        activate: function () {
            utils.header('Создание маршрута');
            utils.show('routeDiv');
            utils.show('routeNameDiv');
            utils.show('routeCoordinatesXDiv');
            utils.show('routeCoordinatesYDiv');
            utils.show('routeFromFramedBlock');
            utils.show('hideFromDiv');
            utils.show('routeFromXDiv');
            utils.show('routeFromYDiv');
            utils.show('routeFromZDiv');
            utils.show('routeFromNameDiv');
            utils.show('routeToXDiv');
            utils.show('routeToYDiv');
            utils.show('routeToZDiv');
            utils.show('routeToNameDiv');
            utils.show('routeDistanceDiv');
        },
        executeRequest: async function () {
            utils.clearErrors();
            utils.resultText('');
            const url = new URL('http://localhost:7000/api/v1/routes');
            const fields = utils.getFields();
            let from = fields.routeFromX === undefined ? '' :
                `<from>
                    <x>${fields.routeFromX}</x>
                    <y>${fields.routeFromY}</y>
                    <z>${fields.routeFromZ}</z>
                    <name>${fields.routeFromName}</name>
                </from>`;
            try {
                const response = await fetch(url, {

                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/xml',
                        'Accept': 'application/xml'
                    },
                    body: `
                    <route>
                    <name>${fields.routeName}</name>
                    <coordinates>
                        <x>${fields.routeCoordinatesX}</x>
                        <y>${fields.routeCoordinatesY}</y>
                    </coordinates>
                    ${from}
                    <to>
                        <x>${fields.routeToX}</x>
                        <y>${fields.routeToY}</y>
                        <z>${fields.routeToZ}</z>
                        <name>${fields.routeToName}</name>
                    </to>
                    <distance>${fields.routeDistance}</distance>
                    </route>
                    `
                });
                const data = await response.text();
                if (response.status === 200) {
                    const route = utils.parseXml(data);
                    utils.resultRouteTable([route]);
                }
                utils.handleNon200Response(response, data);
            } catch (error) {
                console.log(error);
            }
        }
    },
    getRoute: {
        activate: function () {
            utils.header('Поиск маршрута по ID');
            utils.show('routeDiv');
            utils.show('routeIdDiv');
        },
        executeRequest: async function () {
            utils.clearErrors();
            utils.resultText('');
            const fields = utils.getFields();
            const url = new URL(`http://localhost:7000/api/v1/routes/${fields.routeId}`);
            try {
                const response = await fetch(url, {

                    method: 'GET',
                    headers: {
                        'Content-Type': 'application/xml',
                        'Accept': 'application/xml'
                    }
                });
                const data = await response.text();
                if (response.status === 200) {
                    const route = utils.parseXml(data);
                    utils.resultRouteTable([route]);
                }
                utils.handleNon200Response(response, data);
            } catch (error) {
                console.log(error);
            }
        }
    },
    updateRoute: {
        activate: function () {
            utils.header('Изменение маршрута');
            utils.show('routeDiv');
            utils.show('routeIdDiv');
            utils.show('routeNameDiv');
            utils.show('routeCoordinatesXDiv');
            utils.show('routeCoordinatesYDiv');
            utils.show('routeCreationDateDiv');
            utils.show('routeFromFramedBlock');
            utils.show('hideFromDiv');
            utils.show('routeFromXDiv');
            utils.show('routeFromYDiv');
            utils.show('routeFromZDiv');
            utils.show('routeFromNameDiv');
            utils.show('routeToXDiv');
            utils.show('routeToYDiv');
            utils.show('routeToZDiv');
            utils.show('routeToNameDiv');
            utils.show('routeDistanceDiv');
        },
        executeRequest: async function () {
            utils.clearErrors();
            utils.resultText('');
            const fields = utils.getFields();
            const url = new URL(`http://localhost:7000/api/v1/routes/${fields.routeId}`);
            let from = fields.routeFromX === undefined ? '' :
                `<from>
                    <x>${fields.routeFromX}</x>
                    <y>${fields.routeFromY}</y>
                    <z>${fields.routeFromZ}</z>
                    <name>${fields.routeFromName}</name>
                </from>`;
            try {
                const response = await fetch(url, {

                    method: 'PUT',
                    headers: {
                        'Content-Type': 'application/xml',
                        'Accept': 'application/xml'
                    },
                    body: `
                    <route>
                    <id>${fields.routeId}</id>
                    <name>${fields.routeName}</name>
                    <coordinates>
                        <x>${fields.routeCoordinatesX}</x>
                        <y>${fields.routeCoordinatesY}</y>
                    </coordinates>
                    <creationDate>${fields.routeCreationDate}</creationDate>
                    ${from}
                    <to>
                        <x>${fields.routeToX}</x>
                        <y>${fields.routeToY}</y>
                        <z>${fields.routeToZ}</z>
                        <name>${fields.routeToName}</name>
                    </to>
                    <distance>${fields.routeDistance}</distance>
                    </route>
                    `
                });
                const data = await response.text();
                if (response.status === 200) {
                    const route = utils.parseXml(data);
                    utils.resultRouteTable([route]);
                }
                utils.handleNon200Response(response, data);
            } catch (error) {
                console.log(error);
            }
        }
    },
    deleteRoute: {
        activate: function () {
            utils.header('Удаление маршрута по ID');
            utils.show('routeDiv');
            utils.show('routeIdDiv');
        },
        executeRequest: async function () {
            utils.clearErrors();
            utils.resultText('');
            const fields = utils.getFields();
            const url = new URL(`http://localhost:7000/api/v1/routes/${fields.routeId}`);
            try {
                const response = await fetch(url, {

                    method: 'DELETE',
                    headers: {
                        'Content-Type': 'application/xml',
                        'Accept': 'application/xml'
                    }
                });
                const data = await response.text();
                if (response.status === 204) {
                    utils.resultText('Маршрут успешно удален');
                }
                utils.handleNon200Response(response, data);
            } catch (error) {
                console.log(error);
            }
        }
    },
    groupInfo: {
        activate: function () {
            utils.header('Получение информации о группах');
        },
        executeRequest: async function () {
            utils.clearErrors();
            utils.resultText('');
            const url = new URL('http://localhost:7000/api/v1/routes/name-groups-info');
            try {
                const response = await fetch(url, {

                    method: 'GET',
                    headers: {
                        'Content-Type': 'application/xml',
                        'Accept': 'application/xml'
                    }
                });
                const data = await response.text();
                if (response.status === 200) {
                    const groupsInfo = utils.parseXml(data);
                    console.log(groupsInfo);
                    if (Array.isArray(groupsInfo.group))
                        utils.resultGroupsInfo(groupsInfo.group);
                    else
                        utils.resultGroupsInfo([groupsInfo.group]);
                }
                utils.handleNon200Response(response, data);
            } catch (error) {
                console.log(error);
            }
        }
    },
    getDistanceRoute: {
        activate: function () {
            utils.header('Получение маршрутов заданной длины');
            utils.show('routeDiv');
            utils.show('routeDistanceDiv');
        },
        executeRequest: async function () {
            utils.clearErrors();
            utils.resultText('');
            const fields = utils.getFields();
            const url = new URL('http://localhost:7000/api/v1/routes/with-distance-count');
            url.searchParams.append('distance', fields.routeDistance);
            try {
                const response = await fetch(url, {

                    method: 'GET',
                    headers: {
                        'Content-Type': 'application/xml',
                        'Accept': 'application/xml'
                    }
                });
                const data = await response.text();
                if (response.status === 200) {
                    const count = utils.toInt(utils.parseXml(data).count);
                    console.log(count);
                    utils.resultText(`Количество маршрутов с длиной ${fields.routeDistance} : ${count}`);
                }
                utils.handleNon200Response(response, data);
            } catch (error) {
                console.log(error);
            }
        }
    },
    getNavigator: {
        activate: function () {
            utils.header('Получение маршрутов по названиям локаций');
            utils.show('sortingDiv');
            utils.show('sortingFieldDiv');
            utils.show('sortingOrderDiv');
            utils.show('routeDiv');
            utils.show('routeFromFramedBlock');
            utils.show('routeFromNameDiv');
            utils.show('routeToNameDiv');
        },
        executeRequest: async function () {
            utils.clearErrors();
            utils.resultText('');
            const fields = utils.getFields();
            if (fields.sorting === undefined || fields.sorting === null || fields.sorting === '') {
                utils.error('Не указана сортировка');
                return;
            }
            const url = new URL(`http://localhost:7001/api/v1/navigator/routes/${fields.routeFromName}/${fields.routeToName}/${fields.sorting}`);
            try {
                const response = await fetch(url, {
                    method: 'GET',
                    headers: {
                        'Content-Type': 'application/xml',
                        'Accept': 'application/xml'
                    }
                });
                const data = await response.text();
                if (response.status === 200) {
                    const routes = utils.parseXml(data);
                    console.log(routes);
                    if (routes === undefined || routes.routes === undefined || routes.routes.routes === undefined)
                        return;
                    if (Array.isArray(routes.routes.routes))
                        utils.resultRouteTable(routes.routes.routes);
                    else
                        utils.resultRouteTable([routes.routes.routes]);
                }
                utils.handleNon200Response(response, data);
            } catch (error) {
                console.log(error);
            }
        }
    },
    createNavigator: {
        activate: function () {
            utils.header('Создание маршрута по названиям локаций');
            utils.show('routeDiv');
            utils.show('routeFromFramedBlock');
            utils.show('routeNameDiv');
            utils.show('routeCoordinatesXDiv');
            utils.show('routeCoordinatesYDiv');
            utils.show('routeFromNameDiv');
            utils.show('routeToNameDiv');
            utils.show('routeDistanceDiv');

        },
        executeRequest: async function () {
            utils.clearErrors();
            utils.resultText('');
            const fields = utils.getFields();
            const url = new URL(`http://localhost:7001/api/v1/navigator/route/add/${fields.routeFromName}/${fields.routeToName}/${fields.routeDistance}`);
            try {
                const response = await fetch(url, {

                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/xml',
                        'Accept': 'application/xml'
                    },
                    body: `
                    <request>
                    <coordinates>
                        <x>${fields.routeCoordinatesX}</x>
                        <y>${fields.routeCoordinatesY}</y>
                    </coordinates>
                    <name>${fields.routeName}</name>
                    </request>
                    `
                });
                const data = await response.text();
                if (response.status === 200) {
                    const route = utils.parseXml(data);
                    utils.resultRouteTable([route]);
                }
                utils.handleNon200Response(response, data);
            } catch (error) {
                console.log(error);
            }
        }
    }
};

let currentMode = modes.getRoutes;

function applyMode(mode) {
    utils.hideAllFields();
    utils.clearErrors();
    utils.resultText('');
    currentMode = mode;
    currentMode.activate();
}

applyMode(modes.getRoutes);
