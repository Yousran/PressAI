@extends('layouts.layout_table')

@section('styles')
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/5.3.0/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdn.datatables.net/1.13.7/css/dataTables.bootstrap5.min.css">
    <link rel="stylesheet" href="https://cdn.datatables.net/buttons/1.7.1/css/buttons.bootstrap4.min.css">
@endsection

@section('columns')    


<div class="card m-3">
    <div class="card-body">
        <h5 class="card-title">Daftar Mata Kuliah</h5>
        <div class="table-responsive">
            <table id="datatable" class="table" style="width:100%">
                <thead>
                    <tr>
                        <th>Nama Mata Kuliah</th>
                        <th>Kode Mata Kuliah</th>
                        <th>Deskripsi</th>
                        <th></th>
                    </tr>
                </thead>
                <tbody>
                    @foreach ($mata_kuliahs as $mata_kuliah)
                        <tr>
                            <td>{{$mata_kuliah['mata_kuliah_name']}}</td>
                            <td>{{$mata_kuliah['mata_kuliah_code']}}</td>
                            <td>{{$mata_kuliah['desc']}}</td>
                            <td>
                                <div class="btn-group">
                                    <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#edit_{{ $mata_kuliah['mata_kuliah_code'] }}">Edit</button>
                                    <form action="{{ route('mata-kuliah.delete') }}" method="post">
                                        @csrf
                                        <input type="hidden" name="code" value="{{ $mata_kuliah['mata_kuliah_code'] }}">
                                        <button type="submit" class="btn btn-danger">Hapus</button>
                                    </form>
                                </div>
                            </td>
                        </tr>
                    @endforeach
                </tbody>
            </table>
        </div>
    </div>
</div>
@endsection

@push('script')
    <script src="https://code.jquery.com/jquery-3.7.0.js"></script>
    <script src="https://cdn.datatables.net/1.13.7/js/jquery.dataTables.min.js"></script>
    <script src="https://cdn.datatables.net/1.13.7/js/dataTables.bootstrap5.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.29.1/moment.min.js"></script>
    <script src="https://cdn.datatables.net/buttons/1.7.1/js/dataTables.buttons.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jszip/3.1.3/jszip.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.36/pdfmake.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.36/vfs_fonts.js"></script>
    <script src="https://cdn.datatables.net/buttons/1.7.1/js/buttons.html5.min.js"></script>
    <script src="https://cdn.datatables.net/buttons/1.7.1/js/buttons.bootstrap4.min.js"></script>
    
@endpush
