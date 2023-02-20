package com.graduation_project.wicky.csa.model.entity;

import java.util.List;

/**
 * Created by goldze on 2017/7/17.
 */

public class Entity<T> {
    /**
     * content : [{"available":1,"brand":"111","createTime":"2019-03-23 19:26:52","cycleDate":"111","extJson":"","id":1,"inventory":111,"lastModified":"2019-03-23 19:26:52","picture":["/picture/ad5f53a4-01f4-476e-b7c8-04d8e653d355.jpg"],"producerId":5,"productName":"111","productNumber":"1992964846","remark":"111","sourceName":"","unit":"","unitPrice":111,"vedioUrl":"rtmp://jiayou.ante3.com/zhibo/3321?auth_key=1706061534-0-0-1c8fc3a5d1cd05a2d0413c9e68bec991"}]
     * first : true
     * last : true
     * number : 0
     * numberOfElements : 1
     * pageable : {"offset":0,"pageNumber":0,"pageSize":10,"paged":true,"sort":{"sorted":false,"unsorted":true},"unpaged":false}
     * size : 10
     * sort : {"sorted":false,"unsorted":true}
     * totalElements : 1
     * totalPages : 1
     */

    private boolean first;
    private boolean last;
    private int number;
    private int numberOfElements;
    private PageableBean pageable;
    private int size;
    private SortBeanX sort;
    private int totalElements;
    private int totalPages;
    private List<T> content;

    public boolean isFirst() {
        return first;
    }

    public void setFirst(boolean first) {
        this.first = first;
    }

    public boolean isLast() {
        return last;
    }

    public void setLast(boolean last) {
        this.last = last;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getNumberOfElements() {
        return numberOfElements;
    }

    public void setNumberOfElements(int numberOfElements) {
        this.numberOfElements = numberOfElements;
    }

    public PageableBean getPageable() {
        return pageable;
    }

    public void setPageable(PageableBean pageable) {
        this.pageable = pageable;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public SortBeanX getSort() {
        return sort;
    }

    public void setSort(SortBeanX sort) {
        this.sort = sort;
    }

    public int getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(int totalElements) {
        this.totalElements = totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

    public static class PageableBean {
        /**
         * offset : 0
         * pageNumber : 0
         * pageSize : 10
         * paged : true
         * sort : {"sorted":false,"unsorted":true}
         * unpaged : false
         */

        private int offset;
        private int pageNumber;
        private int pageSize;
        private boolean paged;
        private SortBean sort;
        private boolean unpaged;

        public int getOffset() {
            return offset;
        }

        public void setOffset(int offset) {
            this.offset = offset;
        }

        public int getPageNumber() {
            return pageNumber;
        }

        public void setPageNumber(int pageNumber) {
            this.pageNumber = pageNumber;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public boolean isPaged() {
            return paged;
        }

        public void setPaged(boolean paged) {
            this.paged = paged;
        }

        public SortBean getSort() {
            return sort;
        }

        public void setSort(SortBean sort) {
            this.sort = sort;
        }

        public boolean isUnpaged() {
            return unpaged;
        }

        public void setUnpaged(boolean unpaged) {
            this.unpaged = unpaged;
        }

        public static class SortBean {
            /**
             * sorted : false
             * unsorted : true
             */

            private boolean sorted;
            private boolean unsorted;

            public boolean isSorted() {
                return sorted;
            }

            public void setSorted(boolean sorted) {
                this.sorted = sorted;
            }

            public boolean isUnsorted() {
                return unsorted;
            }

            public void setUnsorted(boolean unsorted) {
                this.unsorted = unsorted;
            }
        }
    }

    public static class SortBeanX {
        /**
         * sorted : false
         * unsorted : true
         */

        private boolean sorted;
        private boolean unsorted;

        public boolean isSorted() {
            return sorted;
        }

        public void setSorted(boolean sorted) {
            this.sorted = sorted;
        }

        public boolean isUnsorted() {
            return unsorted;
        }

        public void setUnsorted(boolean unsorted) {
            this.unsorted = unsorted;
        }
    }


}
